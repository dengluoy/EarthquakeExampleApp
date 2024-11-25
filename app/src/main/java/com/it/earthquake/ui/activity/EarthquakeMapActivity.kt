package com.it.earthquake.ui.activity

import android.graphics.Color
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.baidu.location.BDAbstractLocationListener
import com.baidu.location.BDLocation
import com.baidu.location.LocationClient
import com.baidu.location.LocationClientOption
import com.baidu.mapapi.map.BaiduMap
import com.baidu.mapapi.map.CircleOptions
import com.baidu.mapapi.map.InfoWindow
import com.baidu.mapapi.map.MapStatusUpdateFactory
import com.baidu.mapapi.map.MapView
import com.baidu.mapapi.map.MyLocationData
import com.baidu.mapapi.map.Overlay
import com.baidu.mapapi.map.PolygonOptions
import com.baidu.mapapi.map.PolylineOptions
import com.baidu.mapapi.model.LatLng
import com.baidu.mapapi.utils.CoordinateConverter
import com.it.david.utils.log.DavidLogger

import com.it.earthquake.databinding.ActivityEarthquakeMapBinding
import com.it.earthquake.model.Feature
import com.it.earthquake.utils.Constants
import com.it.earthquake.utils.Utils
import kotlin.math.log10
import kotlin.math.sqrt

class EarthquakeMapActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityEarthquakeMapBinding
    private lateinit var mFeature: Feature
    private lateinit var mMap: BaiduMap
    private lateinit var mLocationClient: LocationClient

    inner class EarthquakeLocationListener : BDAbstractLocationListener() {
        override fun onReceiveLocation(location: BDLocation) {
            setLocationData(LatLng(location.latitude, location.longitude))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityEarthquakeMapBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        setupMap()
        internalInitView()
    }

    private fun setupMap() {
        mMap = mBinding.bdMapView.map
        mMap.mapType = BaiduMap.MAP_TYPE_NORMAL
        mMap.isMyLocationEnabled = true
        mMap.setMapStatus(MapStatusUpdateFactory.zoomTo(12.0f))
        mMap.uiSettings.setAllGesturesEnabled(true)

        LocationClient.setAgreePrivacy(true)
        mLocationClient = LocationClient(applicationContext)
        val earthquakeLocationListener = EarthquakeLocationListener()
        initLocation()
        mLocationClient.registerLocationListener(earthquakeLocationListener)
        mLocationClient.start()
        mLocationClient.requestLocation()
    }

    private fun initLocation() {
        val locationOption = LocationClientOption()
        // 高精度、低功耗
        locationOption.locationMode = LocationClientOption.LocationMode.Hight_Accuracy
        locationOption.setCoorType("bd09")

        locationOption.setScanSpan(1000)
        locationOption.setIsNeedAddress(true)
        locationOption.isOpenGnss = true
        locationOption.isLocationNotify = true // GPS 有效频率1s.
        locationOption.setIsNeedLocationDescribe(true)

        locationOption.setIsNeedLocationPoiList(true)
        locationOption.setIgnoreKillProcess(false)
        locationOption.isOpenGnss = true

        locationOption.SetIgnoreCacheException(false)
        locationOption.setEnableSimulateGnss(false)
        mLocationClient.locOption = locationOption
    }

    private fun internalInitView() = with(mBinding) {
        mFeature = intent.getParcelableExtra<Feature>(Constants.EXTRA_ITEM) as Feature

        DavidLogger.i("mFeature geometry : ${mFeature.geometry.coordinates.joinToString("、")}")
        val latlng = CoordinateConverter().from(CoordinateConverter.CoordType.COMMON)
            .coord(LatLng(mFeature.geometry.coordinates[1], mFeature.geometry.coordinates[0]))
            .convert()
        DavidLogger.i("mFeature Coord Latlng : ${latlng}")
        val circleOptions = CircleOptions()
            .center(latlng)
            .radius(Utils.calculateRadius(mFeature.properties.mag, mFeature.geometry.coordinates[2]).toInt())
//            .radius((mFeature.properties.mag * (mFeature.geometry.coordinates[2] * 80)).toInt())
            .fillColor(Color.argb(100, 255, 69, 0))

        val infoWindow = InfoWindow(
            TextView(baseContext).apply {
                text =
                    "震级:${mFeature.properties.mag}\n位置：${mFeature.properties.place}\n深度：${mFeature.geometry.coordinates[2]}"
                setTextColor(Color.BLACK)
                setBackgroundColor(Color.argb(50, 0, 70, 70))
                setPadding(3, 3, 3, 3)
            },
            latlng,
            -50
        )

        mMap.animateMapStatus(MapStatusUpdateFactory.newLatLngZoom(latlng, Utils.calculateResultLinear(
            log10( mFeature.geometry.coordinates[2]))))
        mMap.addOverlay(circleOptions)
        mMap.showInfoWindow(infoWindow)
    }

    private fun setLocationData(latLng: LatLng) {
        mMap.setMyLocationData(
            MyLocationData.Builder()
                .direction(100f).latitude(latLng.latitude)
                .longitude(latLng.longitude).build()
        )
        mMap.animateMapStatus(MapStatusUpdateFactory.newLatLng(latLng))
    }

    override fun onResume() {
        super.onResume()
        mBinding.bdMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mBinding.bdMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mBinding.bdMapView.onDestroy()
        mBinding.bdMapView.setMapCustomStyleEnable(false)
    }

}