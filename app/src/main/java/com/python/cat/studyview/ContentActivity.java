package com.python.cat.studyview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.apkfuns.logutils.LogUtils;
import com.python.cat.studyview.adapter.ContentAdapter;
import com.python.cat.studyview.base.BaseActivity;
import com.python.cat.studyview.bean.ArticleBean;
import com.python.cat.studyview.bean.BannerBean;
import com.python.cat.studyview.bean.FriendBean;
import com.python.cat.studyview.bean.LoginBean;
import com.python.cat.studyview.net.HttpRequest;
import com.python.cat.studyview.utils.DynamicData;
import com.python.cat.studyview.utils.ToastUtils;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class ContentActivity extends BaseActivity {

    private ListView lv;
    private ContentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        LogUtils.w("---- 去执行网络请求吧 -------");
//        requestArticles();
//        requestBanner();
//        requestFriend();

//        requestLogin();

        lv = findViewById(R.id.content_list);
//        lv.setVisibility(View.VISIBLE);
        final List<String> dataList = DynamicData.getDynamicData();
        adapter = new ContentAdapter(getActivity(), dataList);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LogUtils.w("ITEM CLICK:" + parent + view + position + id);
                ToastUtils.show(getApplicationContext(), dataList.get(position));
            }
        });

        RelativeLayout rl = findViewById(R.id.content_rl);
//        rl.setVisibility(View.GONE);
        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.show(getApplicationContext(), "rl click...");
                LogUtils.w("rl click ....");
            }
        });
        Button btn = findViewById(R.id.content_tv);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.i("btn click.........");
                ToastUtils.show(getApplicationContext(), "BUTTON click...");
                startActivity(new Intent(getActivity(), ScrollerActivity.class));
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.release();
        }
        lv = null;
    }

    private void requestLogin() {
        HttpRequest.login("pythoncat", "wanandroid123")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.i(d);
                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                        LogUtils.w(loginBean);
                        LogUtils.e("登录信息====");
                        ToastUtils.show(getApplicationContext(), loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.w("complete....");
                    }
                });
    }

    private void requestBanner() {
        HttpRequest.banner()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BannerBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(d);
                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        LogUtils.w(bannerBean);
                        ToastUtils.show(getApplicationContext(), bannerBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e(t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.w("complete...");
                    }
                });
    }

    private void requestFriend() {
        HttpRequest.friend()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<FriendBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(d);
                    }

                    @Override
                    public void onNext(FriendBean friendBean) {
                        LogUtils.w(friendBean);
                        ToastUtils.show(getApplicationContext(), friendBean);
                    }

                    @Override
                    public void onError(Throwable t) {
                        LogUtils.e(t);
                    }

                    @Override
                    public void onComplete() {
                        LogUtils.w("complete...");
                    }
                });
    }

    private void requestArticles() {
        HttpRequest.article(0)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtils.d(d);
                    }

                    @Override
                    public void onNext(ArticleBean articleBean) {
                        LogUtils.w(articleBean);

                        LogUtils.w("数据加载完成了，可以填充到 view 里面去了~");
                        LogUtils.w("current thread info: "
                                + Thread.currentThread().getName()
                                + ":" + Thread.currentThread().getId());

                        ToastUtils.show(getApplicationContext(), articleBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e);
                    }

                    @Override
                    public void onComplete() {

                        LogUtils.w("complete.....");
                    }
                });
    }
}