package com.example.core;

import android.content.Intent;

import com.example.bean.AddressBean;
import com.example.bean.AllOrderBean;
import com.example.bean.AllOrderUser;
import com.example.bean.BottomBean;
import com.example.bean.CircleBean;
import com.example.bean.DetaileGoods;
import com.example.bean.FootBean;
import com.example.bean.GoodsBanner;
import com.example.bean.GoodsList;
import com.example.bean.PJGoods;
import com.example.bean.Result;
import com.example.bean.SearchGoods;
import com.example.bean.ShoppingBean;
import com.example.bean.ShowBean;
import com.example.bean.TopBean;
import com.example.bean.User;
import com.example.bean.UserWallet;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ILogin {
    @POST("small/user/v1/login")
    @FormUrlEncoded
    Observable<Result<User>> login(@Field("phone") String phone,
                                    @Field("pwd") String password);
    @POST("small/user/v1/register")
    @FormUrlEncoded
    Observable<Result> reg(@Field("phone") String mobile,
                                   @Field("pwd") String password);
    @GET("small/commodity/v1/bannerShow")
    Observable<Result<List<GoodsBanner>>> lunbo();
    @GET("small/commodity/v1/commodityList")
    Observable<Result<GoodsList>>getList();
    @GET("small/circle/v1/findCircleList")
    Observable<Result<List<CircleBean>>> circle(@Query("page") String page,
                                                @Query("count") String count);
    @GET("small/commodity/v1/findCommodityDetailsById")
    Observable<Result<DetaileGoods>> details(@Header("userId") int userId,
                                             @Header("sessionId") String sessionId,
                                             @Query("commodityId") String commodityId);
    @GET("small/commodity/v1/CommodityCommentList")
    Observable<Result<List<PJGoods>>> pj(@Query("commodityId") String commodityId,
                                         @Query("page") String page,
                                         @Query("count") String count);
    @GET("small/commodity/v1/findCommodityByKeyword")
    Observable<Result<List<SearchGoods>>> search(@Query("keyword") String keyword,
                                                 @Query("page") int page,
                                                 @Query("count") int count);
    @GET("small/commodity/v1/findFirstCategory")
    Observable<Result<List<TopBean>>> top();
    @GET("small/commodity/v1/findSecondCategory")
    Observable<Result<List<BottomBean>>> bottom(@Query("firstCategoryId") String firstCategoryId);
    @GET("small/commodity/v1/findCommodityByCategory")
    Observable<Result<List<SearchGoods>>> show(@Query("categoryId") String categoryId,
                                            @Query("page") String page,
                                            @Query("count") String count);
    @GET("small/order/verify/v1/findShoppingCart")
    Observable<Result<List<ShoppingBean>>> findShopping(@Header("userId")int userId, @Header("sessionId")String sessionId);

    @GET("small/user/verify/v1/receiveAddressList")
    Observable<Result<List<AddressBean>>> addresslist(@Header("userId") int userId, @Header("sessionId") String sessionId);
    @POST("small/user/verify/v1/addReceiveAddress")
    @FormUrlEncoded
    Observable<Result> addAddrss(@Header("userId") int userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("realName") String realName,
                                 @Field("phone") String phone,
                                 @Field("address") String address,
                                 @Field("zipCode") String zipCode);

    @GET("small/commodity/verify/v1/browseList")
    Observable<Result<List<FootBean>>> foot(@Header("userId") int userId,
                                            @Header("sessionId") String sessionId,
                                              @Query("page") int page,
                                              @Query("count") int count);
    @GET("small/user/verify/v1/findUserWallet")
    Observable<Result<UserWallet>> wallet(@Header("userId") int userId,
                                          @Header("sessionId") String sessionId,
                                          @Query("page") int page,
                                          @Query("count") int count);
    @PUT("small/order/verify/v1/syncShoppingCart")
    @FormUrlEncoded
    Observable<Result> addTo(@Header("userId") int userId,
                                            @Header("sessionId") String sessionId,
                                            @Field("data") String data);
    @POST("small/user/verify/v1/setDefaultReceiveAddress")
    @FormUrlEncoded
    Observable<Result> moren(@Header("userId") int userId,
                                 @Header("sessionId") String sessionId,
                                 @Field("id") int id);
    //查询全部订单
    @GET("small/order/verify/v1/findOrderListByStatus")
    Observable<Result<List<AllOrderUser<List<AllOrderBean>>>>> AllOrder(@Header("userId") int userId,
                                                                        @Header("sessionId") String sessionId,
                                                                        @Query("status") String status,
                                                                        @Query("page") String page,
                                                                        @Query("count") String count);
    /**
     * 发布圈子
     */
    @POST("small/circle/verify/v1/releaseCircle")
    Observable<Result> releaseCircle(@Header("userId") long userId,
                                     @Header("sessionId")String sessionId,
                                     @Body MultipartBody body);
    //支付
    @POST("small/order/verify/v1/pay")
    @FormUrlEncoded
    Observable<Result> pay(@Header("userId")long userId, @Header("sessionId")String sessionId,@Field("orderId") String orderId,@Field("payType")int payType);

    @POST("small/order/verify/v1/createOrder")
    @FormUrlEncoded
    Observable<Result> createOreder(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Field("orderInfo") String data,
                             @Field("totalPrice") String totalPrice,
                             @Field("addressId") String addressId);
    @PUT("small/order/verify/v1/confirmReceipt")
    @FormUrlEncoded
    Observable<Result> confirmReceipt(@Header("userId") int userId,
                             @Header("sessionId") String sessionId,
                             @Field("orderId") String orderId);
    @DELETE("small/order/verify/v1/deleteOrder")
    Observable<Result> deleteOrder(@Header("userId") int userId,
                                      @Header("sessionId") String sessionId,
                                      @Query("orderId") String orderId);
}
