package com.weidi.inject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.helger.jcodemodel.AbstractJClass;
import com.helger.jcodemodel.JCodeModel;
import com.helger.jcodemodel.JDefinedClass;
import com.helger.jcodemodel.JExpr;
import com.helger.jcodemodel.JInvocation;
import com.helger.jcodemodel.JMethod;

import org.androidannotations.AndroidAnnotationsEnvironment;
import org.androidannotations.helper.APTCodeModelHelper;
import org.androidannotations.holder.EComponentHolder;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;

/**
 使用： </br>
 1、@InjectLayout(R.layout.mainactivity)</br>
 public class MainActivity extends Activity {...}</br>
 2、 在onCreate(Bundle savedInstanceState)方法中加InjectUtils.inject(this);</br>
 3、@InjectView(R.id.button)在控件上必须要有一个id</br>
 private Button button;</br>
 4、onClick()方法和OnLongClickInject()方法中必须要有参数View
 5、单击条目事件的普通方法中的参数必须这样：(AdapterView<?> parent, View view, int position, long id)

 @OnClickInject({R.id.hello1, R.id.hello2})
 void onClick(View v) {}方法名不需要一定是onClick
 @OnLongClickInject({R.id.hello1, R.id.hello2})
 boolean onLongClick(View v) {}方法名不需要一定是onLongClick
 @OnItemClickInject(R.id.list_of_things) void onItemClick(AdapterView<?> parent, View view, int position, long id) {}方法名不需要一定是onItemClick

 Fullscreen */
public class InjectUtils {

    // 动态代理类（必须要实现InvocationHandler接口）
    public static class DynamicProxy implements InvocationHandler {
        Object target = null;// 要代理的对象（真实的对象）
        Method method = null;// 调用真实对象中的某个方法的Method对象

        public DynamicProxy (Object target, Method method) {
            super();
            this.target = target;
            this.method = method;
        }

        @Override
        public Object invoke (Object proxy, Method method, Object[] args) throws Throwable {
            //            if ("OnClickListener".equals(setClickListener)) {
            //                this.method.invoke(target);
            //                return null;
            //            } else if ("OnLongClickListener".equals(setClickListener)) {
            //                this.method.invoke(target);
            //                return true;
            //            } else if ("OnItemClickListener".equals(setClickListener)) {
            //                this.method.invoke(target, args);
            //                return null;
            //            }
            // 调用真实对象中的某个方法后的返回值
            return this.method.invoke(target, args);
        }
    }

    /**
     @param object Activity,Fragment,Adapter
     @param view
     */
    public static void inject (Object object, View view) {
        if (object instanceof Activity) {
            injectLayout((Activity) object);
            injectView(object, null);
            injectExtra((Activity) object);
        } else {
            injectView(object, view);
        }
        injectCommonMethod(object, view, "OnClickListener");
        injectCommonMethod(object, view, "OnLongClickListener");
        injectCommonMethod(object, view, "OnItemClickListener");
        background(object);
    }

    /**
     @param activity
     */
    private static void injectLayout (Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            Class<?> cls = activity.getClass();
            // 得到当前类上的注解类
            InjectLayout mInjectLayout = cls.getAnnotation(InjectLayout.class);
            if (mInjectLayout == null) {
                return;
            }
            int layout = mInjectLayout.value();
            if (layout <= 0) {
                return;
            }
            Method setContentView = cls.getMethod("setContentView", int.class);
            // 调用setContentView()方法
            setContentView.invoke(activity, layout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     绑定组件id

     @param object Activity,Fragment,Adapter
     @param view
     */
    private static void injectView (Object object, View view) {
        try {
            if (object == null) {
                return;
            }
            Class<?> cls = object.getClass();
            Field[] fields = cls.getDeclaredFields();// 得到当前类中所有的属性
            if (fields == null) {
                return;
            }
            for (Field field : fields) {
                field.setAccessible(true);// 设置属性为public
                InjectView mInjectView = field.getAnnotation(InjectView.class);// 得到属性上的注解类
                if (mInjectView == null) {
                    continue;
                }
                int id = mInjectView.value(); // 资源id
                if (id <= 0) {
                    continue;
                }
                Method findViewById = null;
                Object resView = null;
                if (view == null) {
                    findViewById = cls.getMethod("findViewById", int.class);
                    resView = findViewById.invoke(object, id);// 根据id得到相应的控件资源
                } else {
                    findViewById = view.getClass().getMethod("findViewById", int.class);
                    // 调用findViewById()方法得到一个控件
                    resView = findViewById.invoke(view, id);// 根据id得到相应的控件资源
                }
                if (findViewById == null || resView == null || !(resView instanceof View)) {
                    return;
                }
                field.set(object, resView);// 给属性设置为这个资源值
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectCommonMethod (Object object, View v, String clickListener) {
        try {
            if (object == null) {
                return;
            }
            Class<?> cls = object.getClass();
            Method findViewById = null;
            if (v == null) {
                findViewById = cls.getMethod("findViewById", int.class);
            } else {
                findViewById = v.getClass().getMethod("findViewById", int.class);
            }
            if (findViewById == null) {
                return;
            }
            // 得到onClick()方法
            // 得到Activity下所有的方法
            Method[] methods = cls.getDeclaredMethods();
            if (methods == null) {
                return;
            }
            InjectOnClick mInjectOnClick = null;
            InjectOnLongClick mInjectOnLongClick = null;
            InjectOnItemClick mInjectOnItemClick = null;
            Object view = null;
            Object proxy = null;
            Method setClickListener = null;
            int[] ids = null;
            for (Method method : methods) {
                method.setAccessible(true);

                // 得到方法上的注解类，判断是否为null，如果不为null，那么说明要找的方法已经找到了
                if ("OnClickListener".equals(clickListener)) {
                    mInjectOnClick = method.getAnnotation(InjectOnClick.class);
                } else if ("OnLongClickListener".equals(clickListener)) {
                    mInjectOnLongClick = method.getAnnotation(InjectOnLongClick.class);
                } else if ("OnItemClickListener".equals(clickListener)) {
                    mInjectOnItemClick = method.getAnnotation(InjectOnItemClick.class);
                }

                if ("OnClickListener".equals(clickListener)) {
                    if (mInjectOnClick == null) {
                        continue;
                    }
                } else if ("OnLongClickListener".equals(clickListener)) {
                    if (mInjectOnLongClick == null) {
                        continue;
                    }
                } else if ("OnItemClickListener".equals(clickListener)) {
                    if (mInjectOnItemClick == null) {
                        continue;
                    }
                }

                if ("OnClickListener".equals(clickListener)) {
                    ids = mInjectOnClick.value();
                } else if ("OnLongClickListener".equals(clickListener)) {
                    ids = mInjectOnLongClick.value();
                } else if ("OnItemClickListener".equals(clickListener)) {
                    ids = mInjectOnItemClick.value();
                }
                for (int id : ids) {
                    if (v == null) {
                        view = findViewById.invoke(object, id);
                    } else {
                        view = findViewById.invoke(v, id);
                    }
                    if (view == null || !(view instanceof View)) {
                        return;
                    }
                    // 得到setOnClickListener()方法
                    if ("OnClickListener".equals(clickListener)) {
                        /**
                         * 代理的对象只要一个就行了（要代理的是OnClickListener接口对象，所以要用到其定义的onClick()方法的Method对象，不是其定义的不能用）</br>
                         * public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h) throws IllegalArgumentException</br>
                         loader:　　一个ClassLoader对象，定义了由哪个ClassLoader对象来对生成的代理对象进行加载</br>
                         interfaces:　　一个Interface对象的数组，表示的是我将要给我需要代理的对象提供一组什么接口，如果我提供了一组接口给它，那么这个代理对象就宣称实现了该接口(多态)，这样我就能调用这组接口中的方法了</br>
                         h:　　一个InvocationHandler对象，表示的是当我这个动态代理对象在调用方法的时候，会关联到哪一个InvocationHandler对象上</br>
                         http://www.cnblogs.com/xiaoluo501395377/p/3383130.html</br>
                         */
                        proxy = (Object) Proxy.newProxyInstance(View.OnClickListener.class.getClassLoader(), new Class<?>[]{View.OnClickListener.class}, new DynamicProxy(object, method));
                        setClickListener = view.getClass().getMethod("setOnClickListener", View.OnClickListener.class);
                    } else if ("OnLongClickListener".equals(clickListener)) {
                        proxy = (Object) Proxy.newProxyInstance(View.OnLongClickListener.class.getClassLoader(), new Class<?>[]{View.OnLongClickListener.class}, new DynamicProxy(object, method));
                        setClickListener = view.getClass().getMethod("setOnLongClickListener", View.OnLongClickListener.class);
                    } else if ("OnItemClickListener".equals(clickListener)) {
                        proxy = (Object) Proxy.newProxyInstance(AdapterView.OnItemClickListener.class.getClassLoader(), new Class<?>[]{AdapterView.OnItemClickListener.class}, new DynamicProxy(object, method));
                        setClickListener = view.getClass().getMethod("setOnItemClickListener", AdapterView.OnItemClickListener.class);
                    }
                    if (proxy == null || setClickListener == null) {
                        return;
                    }

                    // 给view调用setOnClickListener()方法进行事件的绑定
                    //（proxy是参数，就是OnClickListener匿名对象，然后点击控件调用的方法就是实现InvocationHandler接口中invoke()方法里的代码）
                    setClickListener.invoke(view, proxy);
                    // button.setOnClickListener(new OnClickListener() {...});
                    // 调用setOnClickListener()方法需要一个参数：OnClickListener对象 new OnClickListener() {...}这个东西就需要一个代理了

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void injectExtra (Activity activity) {
        try {
            if (activity == null) {
                return;
            }
            Class<?> cls = activity.getClass();
            Field[] fields = cls.getDeclaredFields();
            if (fields == null) {
                return;
            }
            Method method = cls.getMethod("getIntent");
            if (method == null) {
                return;
            }
            Intent intent = (Intent) method.invoke(activity);
            if (intent == null) {
                return;
            }
            InjectExtra mInjectExtra = null;
            for (Field field : fields) {
                field.setAccessible(true);
                // 得到当前类上的注解类
                mInjectExtra = field.getAnnotation(InjectExtra.class);
                if (mInjectExtra == null) {
                    continue;
                }
                String sBundle = mInjectExtra.bundle();
                String key = mInjectExtra.key();
                // 问题：要先知道传Bundle这个对象的key；然后是这个Bundle对象中的各个key
                Bundle bundle = null;
                bundle = intent.getBundleExtra(sBundle);
                if (bundle != null) {
                    if ("class android.os.Bundle".equals(field.getGenericType().toString())) {
                        field.set(activity, bundle);
                    }
                    if (bundle.containsKey(key)) {
                        Object obj = bundle.get(key);
                        field.set(activity, obj);
                    }
                } else {
                    // 只要传值的时候Intent对象用了putExtra()这个方法，那么bundle就不会为null，否则为null
                    bundle = intent.getExtras();
                    if (bundle == null) {
                        continue;
                    }
                    if (bundle.containsKey(key)) {
                        Object obj = bundle.get(key);
                        field.set(activity, obj);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /****************************************************************************************************************************************************************************/

    /*public static class ThreadDynamicProxy implements InvocationHandler {
        Object target = null;// 要代理的对象（真实的对象）
        Method method = null;// 调用真实对象中的某个方法的Method对象

        public ThreadDynamicProxy (Object target, Method method) {
            super();
            this.target = target;
            this.method = method;
        }

        @Override
        public Object invoke (Object proxy, final Method method, final Object[] args) throws Throwable {
            System.out.println("=================================================================");

            //            MRunnable mRunnable = new MRunnable(proxy, method, args);
            //            ThreadPool.getCachedThreadPool().execute(mRunnable);
            //            return mRunnable.getResult();
            return null;
        }

        private class MRunnable implements Runnable {
            private Object object = null;
            private Object proxy = null;
            private Method method = null;
            private Object[] args = null;

            public MRunnable (Object proxy, final Method method, final Object[] args) {
                this.proxy = proxy;
                this.method = method;
                this.args = args;
            }

            @Override
            public void run () {
                try {
                    object = method.invoke(proxy, args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            public Object getResult () {
                return object;
            }

        }

    }

    /**
     一般使用这个就行了，只要执行任务的时间不超过60s就行了

     @param object
     */
    private static void background (final Object object) {
        if (object == null) {
            return;
        }
        try {
            Class<?> cls = object.getClass();
            Method[] methods = cls.getDeclaredMethods();
            if (methods == null) {
                return;
            }
            Background background = null;
            Object proxy = null;
            Method doThread = null;
            for (final Method method : methods) {
                method.setAccessible(true);
                background = method.getAnnotation(Background.class);
                if (background == null) {
                    continue;
                }
                // 这样子写的话就立即执行方法了，显然不符合要求
                //                method.invoke(object);

                ThreadDynamicProxy mThreadDynamicProxy = new ThreadDynamicProxy(object, method);
                Class<?>[] interfaces = method.getClass().getInterfaces();
                proxy = (Object) Proxy.newProxyInstance(mThreadDynamicProxy.getClass().getClassLoader(), interfaces, mThreadDynamicProxy);
                //                proxy = (Object) Proxy.newProxyInstance(InjectUtils.BackgroundThread.class.getClassLoader(), new Class<?>[]{InjectUtils.BackgroundThread.class}, mThreadDynamicProxy);
                if (proxy == null) {
                    return;
                }
                method.invoke(object, proxy);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void process(Element element, AndroidAnnotationsEnvironment environment, EComponentHolder holder){

        ExecutableElement executableElement = (ExecutableElement) element;

        APTCodeModelHelper codeModelHelper = new APTCodeModelHelper(environment);

        JMethod delegatingMethod = codeModelHelper.overrideAnnotatedMethod(executableElement, holder);

        JCodeModel codeModel = new JCodeModel();

        JDefinedClass anonymousTaskClass = codeModel.anonymousClass(BackgroundExecutor.Task.class);

        Background annotation = element.getAnnotation(Background.class);
        String id = annotation.id();
        int delay = annotation.delay();// delay延迟
        String serial = annotation.serial();// serial连续的

        AbstractJClass backgroundExecutorClass = codeModel.ref(BackgroundExecutor.class);

        JInvocation newTask = JExpr._new(anonymousTaskClass).arg(JExpr.lit(id)).arg(JExpr.lit(delay)).arg(JExpr.lit(serial));

        JInvocation executeCall = backgroundExecutorClass.staticInvoke("execute").arg(newTask);

        delegatingMethod.body().add(executeCall);

    }*/


}
