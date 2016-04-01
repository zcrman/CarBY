package com.BeeFramework.view;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.external.maxwin.view.XListView;

/*
 *	 ______    ______    ______
 *	/\  __ \  /\  ___\  /\  ___\
 *	\ \  __<  \ \  __\_ \ \  __\_
 *	 \ \_____\ \ \_____\ \ \_____\
 *	  \/_____/  \/_____/  \/_____/
 *
 *
 *	Copyright (c) 2013-2014, {Bee} open source community
 *	http://www.bee-framework.com
 *
 *
 *	Permission is hereby granted, free of charge, to any person obtaining a
 *	copy of this software and associated documentation files (the "Software"),
 *	to deal in the Software without restriction, including without limitation
 *	the rights to use, copy, modify, merge, publish, distribute, sublicense,
 *	and/or sell copies of the Software, and to permit persons to whom the
 *	Software is furnished to do so, subject to the following conditions:
 *
 *	The above copyright notice and this permission notice shall be included in
 *	all copies or substantial portions of the Software.
 *
 *	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *	FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 *	IN THE SOFTWARE.
 */
public class MyListView extends XListView {

    private GestureDetector mGestureDetector;
    View.OnTouchListener mGestureListener;
    public LinearLayout bannerView;

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    //   mGestureDetector = new GestureDetector(new YScrollDetector());
        setFadingEdgeLength(0);// listview的上边和下边有黑色的阴影
    }
   //onInterceptTouchEvent()是ViewGroup的一个方法，目的是在系统向该ViewGroup及其各个childView触发onTouchEvent()之前对相关事件进行一次拦截，
   //Android这么设计的想法也很好理解，由于ViewGroup会包含若干childView,因此需要能够统一监控各种touch事件的机会，
   // 因此纯粹的不能包含子view的控件是没有这个方法的，如LinearLayout就有，TextView就没有。
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
    	//GestureDetector.OnDoubleTapListener接口：用来通知DoubleTap事件，类似于鼠标的双击事件。
    	mGestureDetector = new GestureDetector(new YScrollDetector());

        return super.onInterceptTouchEvent(ev) && mGestureDetector.onTouchEvent(ev);
    }
/**
 * 当用户触摸屏幕的时候，会产生许多手势，例如down,up,scroll，filing等等，我们知道View类有个View.OnTouchListener内部接口，
 * 通过重写他的onTouch(View v, MotionEvent event)方法，我们可以处理一些touch事件，但是这个方法太过简单，如果需要处理一些复杂的手势，
 * 用这个接口就会很麻烦（因为我们要自己根据用户触摸的轨迹去判断是什么手势）Android sdk给我们提供了GestureDetector（Gesture：手势Detector：识别）类，
 * 通过这个类我们可以识别很多的手势，主要是通过他的onTouchEvent(event)方法完成了不同手势的识别。虽然他能识别手势，但是不同的手势要怎么处理，
 * 应该是提供给程序员实现的，因此这个类对外提供了两个接口：OnGestureListener，OnDoubleTapListener，还有一个内部类SimpleOnGestureListener，
 * SimpleOnGestureListener类是GestureDetector提供给我们的一个更方便的响应不同手势的类，这个类实现了上述两个接口（但是所有的方法体都是空的），
 * 该类是static class，也就是说它实际上是一个外部类。程序员可以在外部继承这个类，重写里面的手势处理方法。
 * @author Administrator
 *
 */
    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if(distanceY!=0&&distanceX!=0)
            {

            }

            if (null != bannerView)
            {
                Rect rect = new Rect();
                bannerView.getHitRect(rect);

                if (null != e1)
                {
                    if(rect.contains((int)e1.getX(),(int)e1.getY()))
                    {
                        return false;
                    }
                }

                if (null != e2)
                {
                    if(rect.contains((int)e2.getX(),(int)e2.getY()))
                    {
                        return false;
                    }
                }

            }
//            if(Math.abs(distanceY) >= Math.abs(distanceX))
//            {
//                Log.e("listview", "********************** distanceX :" + distanceX + "  distanceY" + distanceY + "\n");
//                return true;
//            }
//            Log.e("listview", "-------------------------- distanceX :" + distanceX + "  distanceY" + distanceY + "\n");
            return true;
        }
    }

}
