package com.qkun.wanandroid_java.http;


import com.blankj.utilcode.util.LogUtils;
import com.qkun.wanandroid_java.http.exception.ApiException;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * 通用的Rx线程转换类
 * 参考:http://www.jcodecraeer.com/a/anzhuokaifa/androidkaifa/2015/0819/3327.html
 */
public class RxSchedulers {

    public static <T> ObservableTransformer<BaseResponse<T>, T> applySchedulers() {
        return new ObservableTransformer<BaseResponse<T>, T>() {
            @Override
            public ObservableSource<T> apply(Observable<BaseResponse<T>> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Function<BaseResponse<T>, ObservableSource<T>>() {
                            @Override
                            public ObservableSource<T> apply(BaseResponse<T> tBaseResponse) throws Exception {
                                if (tBaseResponse.isSuccess()){
                                    return createData(tBaseResponse.getData());
                                }else {
                                    return Observable.error(new ApiException(tBaseResponse.getErrorCode(),tBaseResponse.getErrorMsg()));
                                }
                            }
                        });
            }
        };
    }

    private static <T> ObservableSource<T> createData(final T data) {
        return Observable.create(new ObservableOnSubscribe<T>() {
            @Override
            public void subscribe(ObservableEmitter<T> subscriber) throws Exception {
                try {
                    LogUtils.d("net", "成功 _ onNext");
                    if (null == data) {
                        subscriber.onNext((T) new Object());
                    } else {
                        subscriber.onNext(data);
                    }
                    subscriber.onComplete();
                } catch (Exception e) {
                    LogUtils.d("net", "异常 _ onError");
                    subscriber.onError(e);
                }

            }
        });
    }


//    static final ObservableTransformer schedulersTransformer = new ObservableTransformer() {
//        @Override
//        public ObservableSource apply(Observable upstream) {
//            return (upstream).subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread());
//        }
//    };
//
//
//    public static <T> ObservableTransformer<T, T> applySchedulers() {
//        return (ObservableTransformer<T, T>) schedulersTransformer;
//    }
}
