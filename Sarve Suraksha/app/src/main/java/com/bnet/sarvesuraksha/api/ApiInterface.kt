package com.bnet.sarvesuraksha.api

import com.bnet.sarvesuraksha.model_api.AddVehicleDataMain
import com.bnet.sarvesuraksha.model_api.AdditionalDetailMainData
import com.bnet.sarvesuraksha.model_api.CommonData
import com.bnet.sarvesuraksha.model_api.ContactDetailMainData
import com.bnet.sarvesuraksha.model_api.GetAddedFamilyMemberData
import com.bnet.sarvesuraksha.model_api.GetCartDetailsMainBikeData
import com.bnet.sarvesuraksha.model_api.GetCartDetailsMainData
import com.bnet.sarvesuraksha.model_api.GetIndividualVehicleMainData
import com.bnet.sarvesuraksha.model_api.GetListVehicleMainData
import com.bnet.sarvesuraksha.model_api.GetPincodeDetailsMainGet
import com.bnet.sarvesuraksha.model_api.GetPropserDetailsMainGet
import com.bnet.sarvesuraksha.model_api.GetQouteFormListDataMain
import com.bnet.sarvesuraksha.model_api.GetQuoteFormMainData
import com.bnet.sarvesuraksha.model_api.GetTLAdditionalBasicDetail
import com.bnet.sarvesuraksha.model_api.GetTLAdditionalDetailMainData
import com.bnet.sarvesuraksha.model_api.GetTermLifeQuoteListMainData
import com.bnet.sarvesuraksha.model_api.GetUserDeatilsMainGet
import com.bnet.sarvesuraksha.model_api.GetUserDetailMainData
import com.bnet.sarvesuraksha.model_api.GetVehicleBrandData
import com.bnet.sarvesuraksha.model_api.GetVehicleDetailRcMainData
import com.bnet.sarvesuraksha.model_api.GetVehicleModelMainData
import com.bnet.sarvesuraksha.model_api.GetVehicleRcDetailMain
import com.bnet.sarvesuraksha.model_api.KYCVerificationDocDetailMainData
import com.bnet.sarvesuraksha.model_api.MyPoliciesMainData
import com.bnet.sarvesuraksha.model_api.PostCartDataMain
import com.bnet.sarvesuraksha.model_api.PostCartDataMainTL
import com.bnet.sarvesuraksha.model_api.PostExternalDetailsMainData
import com.bnet.sarvesuraksha.model_api.PostHealthInsConatiner2MainData
import com.bnet.sarvesuraksha.model_api.PostHealthInsuranceMainData
import com.bnet.sarvesuraksha.model_api.PostIdentificationDetailMainData
import com.bnet.sarvesuraksha.model_api.PostNomineDataMainGet
import com.bnet.sarvesuraksha.model_api.PostNomineeDetailMainGet
import com.bnet.sarvesuraksha.model_api.PostOrEditVehicleMainData
import com.bnet.sarvesuraksha.model_api.PostQuoteAdditionalDetailMainData
import com.bnet.sarvesuraksha.model_api.PostSummaryDetailsTLMainData
import com.bnet.sarvesuraksha.model_api.PostTLPersonalDetailMainData
import com.bnet.sarvesuraksha.model_api.PostTLQuoteFromBasicMainData
import com.bnet.sarvesuraksha.model_api.PostUserAddressMainData
import com.bnet.sarvesuraksha.model_api.PostUserDeatilsMainData
import com.bnet.sarvesuraksha.model_api.PostUserProfilePicMainData
import com.bnet.sarvesuraksha.model_api.PostVehcileIndMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleAndUserMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleBasicDetailMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleInsDecMainData
import com.bnet.sarvesuraksha.model_api.PostVehicleMainData
import com.bnet.sarvesuraksha.model_api.UploadImageMainGet
import com.bnet.sarvesuraksha.model_api.UserDeatilMainGet
import com.bnet.sarvesuraksha.model_api.UserIdentificationDetail
import com.bnet.sarvesuraksha.model_api.VehiclePost
import com.bnet.sarvesuraksha.model_api.VerifyDocTypeKycTLMainData
import com.bnet.sarvesuraksha.model_api.VerifyOtpMainGet
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {

    @Headers("Content-Type: application/json")
    @POST("auth/send-otp-b2c")
    fun sendOtp(@Body data: String?): Call<CommonData?>

    @Headers("Content-Type: application/json")
    @POST("auth/verify-otp-b2c")
    fun verifyOtp(@Body data: String?): Call<VerifyOtpMainGet?>

    @GET("auth/find-user-b2c/{mobileNumber}")
    fun findUser(@Path("mobileNumber") mobileNumber: String): Call<UserDeatilMainGet?>


    @GET("user/get-user-detail/{mobileNumber}")
    fun getUserDetail(@Path("mobileNumber") mobileNumber: String): Call<GetUserDeatilsMainGet?>


    @Headers("Content-Type: application/json")
    @POST("user/personal-detail")
    fun postNewUserpersonalDetails(@Body data: String?): Call<PostUserDeatilsMainData?>

    @Headers("Content-Type: application/json")
    @POST("user/personal-detail/{personalId}")
    fun postUserpersonalDetails(@Path("personalId") personalId: String,@Body data: String?): Call<PostUserDeatilsMainData?>


    @Headers("Content-Type: application/json")
    @POST("user/contact-detail/{personalId}")
    fun postContactDetail(@Path("personalId") personalId: String,  @Body data: String?): Call<ContactDetailMainData?>



    @Headers("Content-Type: application/json")
    @POST("user/address-detail/{personalId}")
    fun postUserAddressDetail(@Path("personalId") personalId: String,@Body data: String?): Call<PostUserAddressMainData?>



    @GET("user/get-pincode/{pinCode}")
    fun findPincodedetails(@Path("pinCode") pinCode: String): Call<GetPincodeDetailsMainGet?>

    @Headers("Content-Type: application/json")
    @POST("user/identification-detail/{personalId}")
    fun postIdentificationDetail(@Path("personalId") personalId: String,@Body data: String?): Call<PostIdentificationDetailMainData?>


    @Headers("Content-Type: application/json")
    @POST("user/additional-detail/{personalId}")
    fun postAdditionalDetail(@Path("personalId") personalId: String,@Body data: String?): Call<AdditionalDetailMainData?>

    @GET("user/get-member-list-detail/{mobileNumber}")
    fun getUserMemberDetail(@Path("mobileNumber") mobileNumber: String): Call<GetUserDetailMainData?>


    @GET("user/get-member-detail/{personalId}")
    fun getUserMemberDetailDtl(@Path("personalId") mobileNumber: String): Call<GetAddedFamilyMemberData?>


    @DELETE("user/remove-member-detail/{personalId}")
    fun deleteMemberDetail(@Path("personalId") mobileNumber: String): Call<CommonData?>



    @GET("user/get-rc-detail/{vehicleNumber}")
    fun getVehicleDetail(@Path("vehicleNumber") mobileNumber: String): Call<GetVehicleDetailRcMainData?>


    @Headers("Content-Type: application/json")
    @POST("user/set-vehicle-detail")
    fun postVehicleData(@Body vehiclePost: VehiclePost): Call<PostVehicleMainData>


    @GET("user/get-all-vehicle-detail/{mobileNumber}")
    fun getAllVehicleDetailList(@Path("mobileNumber") mobileNumber: String): Call<GetListVehicleMainData?>

//
    @GET("user/get-all-vehicle-detail/{mobileNumber}")
    suspend fun getAllVehicleDetailListSus(@Path("mobileNumber") mobileNumber: String): Response<GetListVehicleMainData>

    @GET("user/get-vehicle-detail/{vehicleId}")
    fun getIndividualVehicleDetail(@Path("vehicleId") mobileNumber: String): Call<GetIndividualVehicleMainData?>



    @DELETE("user/remove-vehicle-detail/{personalId}")
    fun deleteVehicleDetail(@Path("personalId") mobileNumber: String): Call<CommonData?>

    @GET("user/get-my-purchase-detail/{mobileNumber}")
    fun getAllMyPurchasePolicyData(@Path("mobileNumber") mobileNumber: String): Call<MyPoliciesMainData?>


    @Headers("Content-Type: application/json")
    @POST("motor-insurance/rc-detail")
    fun postRcDetailAndGetRc(@Body data: String?): Call<GetVehicleRcDetailMain>

    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-insurance-detail/{vehicleuniqueid}?vehicleSegment=CAR")
    fun postVehicleInsData(
        @Path("vehicleuniqueid") vehicleuniqueid: String,
        @Body data: String?
    ): Call<PostVehcileIndMainData?>


    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-insurance-detail/{vehicleuniqueid}?vehicleSegment=BIKE")
    fun postVehicleInsDataBike(
        @Path("vehicleuniqueid") vehicleuniqueid: String,
        @Body data: String?
    ): Call<PostVehcileIndMainData?>

    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-insurance-detail/{vehicleuniqueid}?vehicleSegment=BIKE")
    fun postVehicleInsDataBike(
        @Path("vehicleuniqueid") vehicleuniqueid: String,
        @Body data: RequestBody
    ): Call<PostVehcileIndMainData?>

    @GET("motor-insurance/vehicle-maker/CAR")
    fun getCarMakers(@Query("text") searchText: String): Call<GetVehicleBrandData>


    @GET("motor-insurance/vehicle-model/{carId}?CAR")
    fun getCarModels(@Path("carId") mobileNumber: String,@Query("text") searchText: String): Call<GetVehicleModelMainData>



    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-insurance-quote-form")
    fun postVehicleAndUser(@Body vehiclePost: String?): Call<PostVehicleAndUserMainData>



    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-insurance-quote-list")
    fun PostAndGetInsuranceList(@Body vehiclePost: String?): Call<GetQuoteFormMainData>

    @GET("motor-insurance/get-vehicle-detail/{quoteId}?vehicleSegment=CAR")
    fun getAllQouteListTemp(@Path("quoteId") quoteId: String): Call<GetQouteFormListDataMain?>

    @GET("motor-insurance/get-vehicle-detail/{quoteId}?vehicleSegment=BIKE")
    fun getAllQouteListTempBike(@Path("quoteId") quoteId: String): Call<GetQouteFormListDataMain?>

    @POST("upload")
    fun uploadImage(@Body requestBody: RequestBody): Call<UploadImageMainGet>

    @Multipart
    @POST("upload")
    fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("mobileNumber") mobileNumber: RequestBody,
        @Part("docType") docType: RequestBody,
        @Part("docId") docId: RequestBody
    ): Call<UploadImageMainGet>



    @Headers("Content-Type: application/json")
    @POST("user/profile-picture-detail/{personalId}")
    fun postUserProfilePic(@Path("personalId") personalId: String,@Body data: String?): Call<PostUserProfilePicMainData?>

    @GET("get/{profilePhoto}")
    fun profilePhoto(
        @Path("profilePhoto") profilePhoto: String
    ): Call<ResponseBody>


    @GET("motor-insurance/get-vehicle-detail/{vehicleId}?vehicleSegment=CAR")
    fun getVehicleDetailsAdd(@Path("vehicleId") quoteId: String): Call<AddVehicleDataMain?>

    @GET("motor-insurance/get-vehicle-detail/{vehicleId}?vehicleSegment=BIKE")
    fun getVehicleDetailsAddBike(@Path("vehicleId") quoteId: String): Call<AddVehicleDataMain?>


    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-detail")
    fun addVehicleDetails(@Body vehiclePost: String?): Call<PostOrEditVehicleMainData?>



    @Headers("Content-Type: application/json")
    @POST("motor-insurance/cart-detail")
    fun postCartDataMain(@Body cartData: String?): Call<PostCartDataMain?>



    @GET("motor-insurance/summary-detail/{quoteId}?vehicleSegment=CAR")
    fun getSummaryDetails(@Path("quoteId") quoteId: String): Call<GetCartDetailsMainData?>

    @GET("motor-insurance/summary-detail/{quoteId}?vehicleSegment=BIKE")
    fun getSummaryDetailsBike(@Path("quoteId") quoteId: String): Call<GetCartDetailsMainBikeData?>


    @Headers("Content-Type: application/json")
    @POST("motor-insurance/doc-detail")
    fun verifyKyc(@Body data: String?): Call<KYCVerificationDocDetailMainData?>



    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-owner-detail/{vehicleId}")
    fun postNomineeDetails(@Path("vehicleId") personalId: String,@Body data: String?): Call<PostNomineDataMainGet?>



    @Headers("Content-Type: application/json")
    @POST("motor-insurance/nomine-detail/{vehicleId}")
    fun postNomineeDetails1(@Path("vehicleId") personalId: String,@Body data: String?): Call<PostNomineeDetailMainGet?>


    @GET("motor-insurance/proposer-detail/{quoteId}?vehicleSegment=CAR")
    fun getPropserDetails(@Path("quoteId") quoteId: String): Call<GetPropserDetailsMainGet?>


    @GET("motor-insurance/proposer-detail/{quoteId}?vehicleSegment=BIKE")
    fun getPropserDetailsBike(@Path("quoteId") quoteId: String): Call<GetPropserDetailsMainGet?>

    @Headers("Content-Type: application/json")
    @POST("motor-insurance/external-detail/{vehicleId}")
    fun postExternalData(@Path("vehicleId") personalId: String,@Body data: String?): Call<PostExternalDetailsMainData?>



    @Headers("Content-Type: application/json")
    @POST("motor-insurance/vehicle-basic-detail/{vehicleId}")
    fun postvehicleBasicDetail(@Path("vehicleId") personalId: String,@Body data: String?): Call<PostVehicleBasicDetailMainData?>


    @Headers("Content-Type: application/json")
    @POST("motor-insurance/declaration-detail/{vehicleId}")
    fun postDeclarationDetail(@Path("vehicleId") personalId: String,@Body data: String?): Call<PostVehicleInsDecMainData?>

    @Headers("Content-Type: application/json")
    @POST("health_insurance/quote-form-basic-detail")
    fun postQouteFormBasicDetail(@Body data: String?): Call<PostHealthInsuranceMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("health_insurance/quote-form-personal-detail/{healthID}")
    fun postQouteFormBasicDetailC2(@Path("healthID") healthID: String,@Body data: String?): Call<PostHealthInsConatiner2MainData?>

    @Headers("Content-Type: application/json")
    @POST("termlife_insurance/quote-form-basic-detail")
    fun postQouteFormBasicDetailTL(@Body data: String?): Call<PostTLQuoteFromBasicMainData?>


    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/quote-form-personal-detail/{termLifeID}")
    fun postQouteFormTLC2(@Path("termLifeID") termLifeID: String,@Body data: String?): Call<PostTLPersonalDetailMainData?>


    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/quote-additional-detail/{termLifeID}")
    fun postQuoteAdditionalDetail(@Path("termLifeID") termLifeID: String,@Body data: String?): Call<PostQuoteAdditionalDetailMainData?>

    @Headers("Content-Type: application/json")
    @POST("termlife_insurance/termlife-insurance-quote-list/{quoteId}")
    fun postAndGetTLQuote(@Path("quoteId") termLifeID: String,@Body data: String?): Call<GetTermLifeQuoteListMainData?>


    @Headers("Content-Type: application/json")
    @POST("termlife_insurance/summary-detail")
    fun postSummaryDetailsTL(@Body data: String?): Call<PostSummaryDetailsTLMainData?>

    @Headers("Content-Type: application/json")
    @POST("termlife_insurance/cart-detail")
    fun postCartDataMainTL(@Body data: String?): Call<PostCartDataMainTL?>

    @GET("termlife_insurance/proposer-detail/{quoteId}")
    fun getDocTypeKycTLMainData(@Path("quoteId") quoteId: String): Call<VerifyDocTypeKycTLMainData?>


    @Headers("Content-Type: application/json")
    @POST("termlife_insurance/proposer-doc-detail")
    fun verifyDocTypeKycTL(@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-detail/{quoteId}")
    fun patchPropserDetails(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-address-detail/{quoteId}")
    fun patchAddressDetails(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-contact-detail/{quoteId}")
    fun patchContactDetails(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-member-detail/{quoteId}")
    fun patchMemberDetails(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @GET("termlife_insurance/termLife-quote-form/{quoteId}")
    fun getAdditionalDetails(@Path("quoteId") quoteId: String): Call<GetTLAdditionalDetailMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-additional-detail/{quoteId}")
    fun patchPropserAdditionalDtl(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-medical-detail/{quoteId}")
    fun patchMedicalDetail(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>

    @Headers("Content-Type: application/json")
    @PATCH("termlife_insurance/proposer-nominee-detail/{quoteId}")
    fun patchPropserNomineeDetail(@Path("quoteId") quoteId: String,@Body data: String?): Call<VerifyDocTypeKycTLMainData?>


}
