package com.komiut.conductor.retrofit;

import com.komiut.conductor.model.Booking;
import com.komiut.conductor.model.CarLocation;
import com.komiut.conductor.model.CashTransaction;
import com.komiut.conductor.ui.passenger.Boarding;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface APIService {

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/c/profile")
    Call<UserIn> getUserData();

    @POST("api/logout")
    Call<LogoutResponse> logout(@Header("Authorization") String access_token);


    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/c/transactions")
    Call<List<TransactionResponse>> getTransactions();

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/c/queues")
    Call<List<QueueResponse>> getQueues(@Query("regno") String plate);

    @POST("api/login")
    Call<LoginResponse> login(@Body LoginRequest loginDataOut);

    @POST("api/c/add/cash")
    Call<CashTransactionResponse> PayCash(@Header("Authorization") String access_token, @Body CashTransaction cashTransaction);


    @POST("api/c/save/mpesa")
    Call<MpesaServerResponse> SendMpesaMessages(@Header("Authorization") String access_token, @Body MpesaServerData mpesaServerData);

    @POST("api/c/add/location")
    Call<CarLocationResponse> SaveLocation(@Header("Authorization") String access_token, @Body CarLocation carLocation);

    @POST("api/c/routes")
    Call<List<RouteResponce>> FetchRoutes(@Header("Authorization") String access_token, @Body RouteRequest routeRequest);

    @GET("api/c/stages")
    Call<List<Stage>> getStages(@Header("Authorization") String access_token, @Body Stage stage);

    /*   @POST("api/c/request_queue")
       Call<QueueRequestResponse> RequestQueue(@Body QueueRequest queueRequest);
   */
    @POST("api/c/request_queue")
    Call<QueueRequestResponse> RequestQueue(@Header("Authorization") String access_token, @Body QueueRequest queueRequest);

    @GET("api/c/current_route")
    Call<List<SubRoutesResponse>> GetSubRoutes(@Header("Authorization") String access_token);

    @GET("api/c/sacco/info")
    Call<SaccoInfo> saccoInfo(@Header("Authorization") String access_token);

    @GET("api/c/current_route")
    Call<MatatuRoute> Routes(@Header("Authorization") String access_token);

    @Headers({"Content-Type: application/json", "Accept: application/json"})
    @GET("api/c/passengers")
    Call<List<Boarding>> getPassengers(@Header("Authorization") String access_token);


    @POST("api/c/passengers/board")
    @Headers({"X-Requested-With: XMLHttpRequest",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    Call<Booking> board(@Header("Authorization") String access_token, @Field("id") int id);

    @POST("api/c/passengers/cancel")
    @Headers({"X-Requested-With: XMLHttpRequest",
            "Content-Type: application/x-www-form-urlencoded"
    })
    @FormUrlEncoded
    Call<Booking> cancelBooking(@Header("Authorization") String access_token, @Field("id") int id);


}
