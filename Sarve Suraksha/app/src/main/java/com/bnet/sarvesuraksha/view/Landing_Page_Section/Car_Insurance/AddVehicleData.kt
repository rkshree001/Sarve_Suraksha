package com.bnet.sarvesuraksha.view.Landing_Page_Section.Car_Insurance

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bnet.sarvesuraksha.api.APIClient
import com.bnet.sarvesuraksha.api.ApiInterface
import com.bnet.sarvesuraksha.api.MyApplicationPort3010
import com.bnet.sarvesuraksha.model_api.GetVehicleBrandData
import com.bnet.sarvesuraksha.model_api.AddVehicleDataMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.widget.GridView
import android.widget.RadioButton
import android.widget.RadioGroup
import com.bnet.sarvesuraksha.model_api.GetVehicleModelMainData
import com.bnet.sarvesuraksha.model_api.PostOrEditVehicleMainData
import com.bnet.savresuraksha.R
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.*
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddVehicleData : AppCompatActivity() {

    private lateinit var carNumber: TextInputEditText
    private lateinit var yearSelect: TextInputEditText
    private lateinit var selectCarNumber: LinearLayout
    private lateinit var ToolBarText: TextView
    private lateinit var goBack: LinearLayout
    private lateinit var save_LL: LinearLayout
    private val allCarMakersList = mutableListOf<String>()
    private val displayedCarMakersList = mutableListOf<String>()

    private val allModelNamesList = mutableListOf<String>()
    private val displayedallModelNamesList = mutableListOf<String>()
    private var debounceJob: Job? = null
    private val debounceDelay = 300L
    private lateinit var autoCompleteTextViewCarMakers: AutoCompleteTextView
    private lateinit var searchVariant: AutoCompleteTextView
    private lateinit var searchFuelType: AutoCompleteTextView
    private lateinit var searchRTO: AutoCompleteTextView
    private lateinit var searchCity: AutoCompleteTextView
    private lateinit var searchModel: AutoCompleteTextView
    private lateinit var radioGroupVehType: RadioGroup
    private lateinit var privateradio: RadioButton
    private lateinit var commerical: RadioButton
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_vehicle_data)

        ToolBarText = findViewById(R.id.ToolBarText)
        goBack = findViewById(R.id.goBack)
        selectCarNumber = findViewById(R.id.selectCarNumber)
        carNumber = findViewById(R.id.carNumber)
        autoCompleteTextViewCarMakers = findViewById(R.id.autoCompleteTextViewCarMakers)
        searchVariant = findViewById(R.id.searchVariant)
        yearSelect = findViewById(R.id.yearSelect)
        searchModel = findViewById(R.id.searchModel)
        searchRTO = findViewById(R.id.searchRTO)
        searchCity = findViewById(R.id.searchCity)
        searchFuelType = findViewById(R.id.searchFuelType)
        radioGroupVehType = findViewById(R.id.radioGroupVehType)
        privateradio = findViewById(R.id.privateradio)
        commerical = findViewById(R.id.commerical)
        save_LL = findViewById(R.id.save_LL)


        yearSelect.setOnClickListener {
            showYearPickerDialog()
        }

        adapter = ArrayAdapter(
            this@AddVehicleData,
            android.R.layout.simple_dropdown_item_1line,
            displayedCarMakersList
        )
        autoCompleteTextViewCarMakers.setAdapter(adapter)

        adapter = ArrayAdapter(
            this@AddVehicleData,
            android.R.layout.simple_dropdown_item_1line,
            displayedallModelNamesList
        )
        searchModel.setAdapter(adapter)

        Log.d("AutoCompleteDATAS", "Filtered Makers List: $displayedCarMakersList")

        fetchVehicleBrandData()

        val intent = intent
        val type = intent.getStringExtra("Type")
        val vehicleIdIn = intent.getStringExtra("vehicleId")

        Log.d("vehicleIdInADS", "vehicleIdIn: $vehicleIdIn")

//        if (vehicleIdIn != null) {
//            GetVehicleData(vehicleIdIn)
            GetVehicleData("66b1c4f6c7210f616925c143")
//        }
        if (type != null && type == "Edit") {
            ToolBarText.text = "Edit your car details"
        }

        goBack.setOnClickListener {
            val intent = Intent(this@AddVehicleData, CarInsurance::class.java)
            startActivity(intent)
        }


        save_LL.setOnClickListener {
            VehiclePost()
        }


/*        autoCompleteTextViewCarMakers.setOnItemClickListener { parent, view, position, id ->
//          working

            val selectedMaker = parent.getItemAtPosition(position) as String
            autoCompleteTextViewCarMakers.setText(selectedMaker)

            Log.d("AutoCompleteDATAS", "Selected Maker: $selectedMaker at position: $position")

            autoCompleteTextViewCarMakers.dismissDropDown()
        }*/


        autoCompleteTextViewCarMakers.setOnItemClickListener { parent, view, position, id ->
            val selectedMaker = parent.getItemAtPosition(position) as String
            val originalPosition = allCarMakersList.indexOf(selectedMaker)

            Log.d("AutoCompleteDATAS", "Filtered position: $position, Original position: $originalPosition")
            Log.d("AutoCompleteDATAS", "Selected Maker: $selectedMaker at original position: $originalPosition")

            autoCompleteTextViewCarMakers.setText(selectedMaker)
            autoCompleteTextViewCarMakers.dismissDropDown()
        }




        autoCompleteTextViewCarMakers.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

                if (displayedCarMakersList.isNotEmpty()) {
                    autoCompleteTextViewCarMakers.post {
                        autoCompleteTextViewCarMakers.showDropDown()
                    }
                }
            } else {

                autoCompleteTextViewCarMakers.dismissDropDown()
            }
        }



        autoCompleteTextViewCarMakers.setOnClickListener {

            if (displayedCarMakersList.isNotEmpty()) {
                autoCompleteTextViewCarMakers.showDropDown()
            }
        }


        /*Search Model Spinner*/

        searchModel.setOnItemClickListener { parent, view, position, id ->
            val selectedMaker = parent.getItemAtPosition(position) as String
            val originalPosition = allModelNamesList.indexOf(selectedMaker)

            Log.d("AutoCompleteDATAS", "Filtered position: $position, Original position: $originalPosition")
            Log.d("AutoCompleteDATAS", "Selected Maker: $selectedMaker at original position: $originalPosition")

            searchModel.setText(selectedMaker)
            searchModel.dismissDropDown()
        }


        searchModel.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {

                if (displayedallModelNamesList.isNotEmpty()) {
                    searchModel.post {
                        searchModel.showDropDown()
                    }
                }
            } else {

                searchModel.dismissDropDown()
            }
        }

        searchModel.setOnClickListener {

            if (displayedallModelNamesList.isNotEmpty()) {
                searchModel.showDropDown()
            }
        }

/*
        autoCompleteTextViewCarMakers.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                debounceJob?.cancel()
                debounceJob = CoroutineScope(Dispatchers.Main).launch {
                    delay(debounceDelay)
                    filterCarMakers(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

*/


    }


    private fun showYearPickerDialog() {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val startYear = 1964

        val dialogView = layoutInflater.inflate(R.layout.dialog_year_list, null)
        val gridViewYears = dialogView.findViewById<GridView>(R.id.gridViewYears)


        val years = (startYear..currentYear).map { it.toString() }
        val adapter = ArrayAdapter(this, R.layout.grid_item_year, R.id.textViewYear, years)
        gridViewYears.adapter = adapter



        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialog.show()

        gridViewYears.setOnItemClickListener { _, _, position, _ ->
            val selectedYear = years[position]
            yearSelect.setText(selectedYear)
            dialog.dismiss()
        }

    }



    private fun GetVehicleData(vehicleIdIn: String?) {
        if (!APIClient.isNetworkAvailable(this@AddVehicleData)) {
            Toast.makeText(this@AddVehicleData, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }
        Log.d("GetVehicleDataAA", "Request: $vehicleIdIn")
        val call: Call<AddVehicleDataMain?>? =
            MyApplicationPort3010.getRetrofitClient(this@AddVehicleData).create<ApiInterface>(ApiInterface::class.java)
                .getVehicleDetailsAdd(vehicleIdIn.toString())
//                .getVehicleDetailsAdd(vehicleIdIn.toString())
        call?.enqueue(object : Callback<AddVehicleDataMain?> {
            override fun onResponse(call: Call<AddVehicleDataMain?>, response: Response<AddVehicleDataMain?>) {
                if (response.isSuccessful) {
                    val main: AddVehicleDataMain? = response.body()
                    main?.let {

                        val vehicleType = "${main.data?.vehicleDetail?.makerName} ${main.data?.vehicleDetail?.modelName}"
                        val vehicleTypeVal = "${main.data?.vehicleDetail?.variantName} - ${main.data?.vehicleDetail?.fuelType}"
                        val makerName = "${main.data?.vehicleDetail?.makerName}"
                        val modelName = "${main.data?.vehicleDetail?.modelName}"
                        val variantName = "${main.data?.vehicleDetail?.variantName}"
                        val carRegNo = main.data?.vehicleDetail?.registrationNumber
                        val fuelType = main.data?.vehicleDetail?.fuelType
                        val registrationDate = main.data?.vehicleDetail?.registrationDate
                        val cityName = main.data?.vehicleDetail?.cityName
                        val rtoCode = main.data?.vehicleDetail?.rtoCode
                        val typeOfVehicle = main.data?.vehicleDetail?.typeOfVehicle

                        Log.d("carRegNoAA", "onResponse: $carRegNo")
                        carNumber.setText(carRegNo ?: "")
                        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
                        val date = registrationDate?.let { inputDateFormat.parse(it) }
                        val outputDateFormat = SimpleDateFormat("yyyy", Locale.getDefault())
                        val year = date?.let { outputDateFormat.format(it) }
//                        if (allCarMakersList.contains(makerName)) {
                        autoCompleteTextViewCarMakers.setText(makerName)
                        searchModel.setText(modelName)
                        searchVariant.setText(variantName)
                        searchFuelType.setText(fuelType)
                        searchCity.setText(cityName)
                        searchRTO.setText(rtoCode)
                        yearSelect.setText(year ?: "")


                        if (typeOfVehicle.equals("Private", ignoreCase = true)) {
                            privateradio.isChecked = true
                        } else {
                            commerical.isChecked = true
                        }

//                        }


                        val json = createVehicleJsonFromData(main)


                        Toast.makeText(this@AddVehicleData, response.body()?.message ?: "No message", Toast.LENGTH_SHORT).show()
                        Log.d("GetVehicleDataAA", "Api Response: ${response.code()} - ${response.message()}~~${response.raw()}")
                    }
                } else {
                    Log.d("GetVehicleDataAA", "Server error: ${response.code()} - ${response.message()}~~${response.raw()}")
                    Toast.makeText(this@AddVehicleData, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<AddVehicleDataMain?>?, t: Throwable) {
                Log.e("GetVehicleDataAA", "Failed to get user details", t)
                Toast.makeText(this@AddVehicleData, "Server Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchVehicleBrandData() {
        if (!APIClient.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("GetVehicleData", "Fetching all vehicle brands")
        val call: Call<GetVehicleBrandData>? = MyApplicationPort3010.getRetrofitClient(this@AddVehicleData)
            .create<ApiInterface>(ApiInterface::class.java)
            .getCarMakers("")

        call?.enqueue(object : Callback<GetVehicleBrandData?> {
            override fun onResponse(call: Call<GetVehicleBrandData?>, response: Response<GetVehicleBrandData?>) {
                if (response.isSuccessful) {
                    val main: GetVehicleBrandData? = response.body()
                    main?.let {
                        allCarMakersList.clear()
                        it.data?.forEach { brandDataRes ->
                            brandDataRes.makerName?.let { makerName ->
                                Log.d("GetVehicleData", "API Response: $makerName")
                                allCarMakersList.add(makerName)
                            }
                        }


                        displayedCarMakersList.clear()
                        displayedCarMakersList.addAll(allCarMakersList)
                        adapter.notifyDataSetChanged()

                        autoCompleteTextViewCarMakers.showDropDown()
                        Log.d("GetVehicleData", "Adapter updated with ${allCarMakersList.size} items")
                    }
                } else {
                    Log.d("GetVehicleData", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@AddVehicleData, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetVehicleBrandData?>?, t: Throwable) {
                Log.e("GetVehicleData", "Failed to get vehicle brands", t)
                Toast.makeText(this@AddVehicleData, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getCarModels() {
        if (!APIClient.isNetworkAvailable(this)) {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("getCarModels", "Fetching all vehicle brands")
        val call: Call<GetVehicleModelMainData>? = MyApplicationPort3010.getRetrofitClient(this@AddVehicleData)
            .create<ApiInterface>(ApiInterface::class.java)
            .getCarModels("","")

        call?.enqueue(object : Callback<GetVehicleModelMainData?> {
            override fun onResponse(call: Call<GetVehicleModelMainData?>, response: Response<GetVehicleModelMainData?>) {
                if (response.isSuccessful) {
                    val main: GetVehicleModelMainData? = response.body()
                    main?.let {
                        allModelNamesList.clear()
                        it.data?.forEach { brandDataRes ->
                            brandDataRes.modelName?.let { modelName ->
                                Log.d("getCarModels", "API Response: $modelName")
                                allModelNamesList.add(modelName)
                            }
                        }


                        displayedallModelNamesList.clear()
                        displayedallModelNamesList.addAll(allModelNamesList)
                        adapter.notifyDataSetChanged()
                        searchModel.showDropDown()

                        Log.d("getCarModels", "Adapter updated with ${allModelNamesList.size} items")
                    }
                } else {
                    Log.d("getCarModels", "Server error: ${response.code()} - ${response.message()}")
                    Toast.makeText(this@AddVehicleData, "Server Error: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GetVehicleModelMainData?>?, t: Throwable) {
                Log.e("getCarModels", "Failed to get vehicle brands", t)
                Toast.makeText(this@AddVehicleData, "Error: ${t.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filterCarMakers(query: String) {
        val filteredList = if (query.isEmpty()) {
            allCarMakersList
        } else {
            allCarMakersList.filter { it.contains(query, ignoreCase = true) }
        }

        Log.d("FilterCarMakers", "Query: $query")
        Log.d("FilterCarMakers", "Filtered List: $filteredList")

        displayedCarMakersList.clear()
        displayedCarMakersList.addAll(filteredList)
        adapter.notifyDataSetChanged()


        autoCompleteTextViewCarMakers.post {
            if (filteredList.isNotEmpty()) {
                autoCompleteTextViewCarMakers.showDropDown()
            } else {
                autoCompleteTextViewCarMakers.dismissDropDown()
            }
        }
    }

    private fun createVehicleJsonFromData(vehicleData: AddVehicleDataMain?) : JSONObject {
        val json = JSONObject()

        vehicleData?.let {

            val vehicleDetail = JSONObject().apply {
                put("makerName", it.data?.vehicleDetail?.makerName ?: "")
                put("modelName", it.data?.vehicleDetail?.modelName ?: "")
                put("variantName", it.data?.vehicleDetail?.variantName ?: "")
                put("fuelType", it.data?.vehicleDetail?.fuelType ?: "")
                put("vehicleSegment", it.data?.vehicleDetail?.vehicleSegment ?: "")
                put("typeOfVehicle", it.data?.vehicleDetail?.typeOfVehicle ?: "")


                val rtoDetail = JSONObject().apply {
                    put("rtoCode", it.data?.vehicleDetail?.rtoCode ?: "")
                    put("cityName", it.data?.vehicleDetail?.cityName ?: "")
                }
                put("rtoDetail", rtoDetail)
            }
            json.put("vehicleDetail", vehicleDetail)


            val vehicleBasicDetail = JSONObject().apply {
                put("registrationNumber", it.data?.vehicleDetail?.registrationNumber ?: "")
                put("registrationDate", it.data?.vehicleDetail?.registrationDate ?: "")
            }
            json.put("vehicleBasicDetail", vehicleBasicDetail)

            json.put("vehicleType", "vehicle_With_out_number") // or any other logic to determine vehicleType
        }

        return json
    }

    private fun createVehicleJson(): JSONObject {
        val json = JSONObject()


        val vehicleDetail = JSONObject().apply {
            put("makerName", autoCompleteTextViewCarMakers.text.toString())
            put("modelName", searchModel.text.toString())
            put("variantName", searchVariant.text.toString())
            put("fuelType", searchFuelType.text.toString())
            put("vehicleSegment", "CAR")

            val typeOfVehicle = if (commerical.isChecked) "commercial" else "private"
            put("typeOfVehicle", typeOfVehicle)

            val rtoDetail = JSONObject().apply {
                put("rtoCode", searchRTO.text.toString())
                put("cityName", searchCity.text.toString())
            }
            put("rtoDetail", rtoDetail)
        }
        json.put("vehicleDetail", vehicleDetail)


        val vehicleBasicDetail = JSONObject().apply {
            put("registrationNumber", carNumber.text.toString())
            put("registrationDate", "/01/01")
        }
        json.put("vehicleBasicDetail", vehicleBasicDetail)

        json.put("vehicleType", "vehicle_With_out_number")

        return json
    }


    private fun VehiclePost() {
        val json: JSONObject = createVehicleJson()
        json.put("", "")

        Log.d("VehiclePost", "Resp : "+json.toString())
        val call: Call<PostOrEditVehicleMainData?>? = MyApplicationPort3010
            .getRetrofitClient(this@AddVehicleData)
            .create<ApiInterface>(ApiInterface::class.java)
            .addVehicleDetails(json.toString())
        call?.enqueue(object : Callback<PostOrEditVehicleMainData?> {
            override fun onResponse(
                call: Call<PostOrEditVehicleMainData?>,
                response: retrofit2.Response<PostOrEditVehicleMainData?>
            ) {
                if (response.isSuccessful()) {
                    val main: PostOrEditVehicleMainData? = response.body()
                    try {
                        if (response.isSuccessful){

                            Log.d("VehiclePost", "onResponse 1: "+response.raw()+"~"+json.toString())

                            Toast.makeText(this@AddVehicleData, response.body()!!.message, Toast.LENGTH_SHORT).show()
                        }
                    } catch (e: Exception) {
                        Log.d("VehiclePost", "onResponse 2: "+response.raw()+"~"+json.toString())
                        Toast.makeText(this@AddVehicleData, e.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.d("VehiclePost", "onResponse 3: "+response.raw()+"~"+json.toString())
                    Toast.makeText(this@AddVehicleData, response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            public override fun onFailure(call: Call<PostOrEditVehicleMainData?>?, t: Throwable) {
                Log.d("VehiclePost", "onResponse: "+t.localizedMessage+"~"+json.toString())
                Toast.makeText(this@AddVehicleData, "Server Error " + t.toString(), Toast.LENGTH_SHORT).show()
            }


        })
    }




}
