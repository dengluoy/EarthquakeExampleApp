package com.it.earthquake.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.it.earthquake.databinding.ActivityMainBinding
import com.it.earthquake.model.EarthquakeResponse
import com.it.earthquake.model.Feature
import com.it.earthquake.service.APIClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class EarthquakeListViewModel : ViewModel() {
    val mEarthquakeFeature = MutableLiveData<ArrayList<Feature>>()
    val mErrorMessage = MutableLiveData<Boolean>()
    val mProgressStatus = MutableLiveData<Boolean>()
    private val mDisposable = CompositeDisposable()

    fun fetchData() {
        tumEarthquake()
    }

    private fun tumEarthquake() {
        mProgressStatus.value = true
        mDisposable.add(
            APIClient.APIService.getEarthquakes()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<EarthquakeResponse>() {
                    override fun onSuccess(t: EarthquakeResponse) {
                        Log.i(">>>>>", t.toString())
                        earthquakeGoster(t.features)
                    }

                    override fun onError(e: Throwable) {
//                        Log.i(">>>>>", t.toString())
                        mProgressStatus.value = false
                        mErrorMessage.value = true
                        e.printStackTrace()
                    }
                })
        )
    }

    private fun earthquakeGoster(liste: ArrayList<Feature>) {
        mEarthquakeFeature.value = liste
        mProgressStatus.value = false
        mErrorMessage.value = false
    }

    fun listGunceleSwipe(binding: ActivityMainBinding) {
        // Swipe Refresh Layout.

        binding.swipeRefreshView.setOnRefreshListener {
            binding.earthquakeProgressBar.visibility = View.GONE
            binding.earthquakeTv.visibility = View.GONE
            binding.earthquakeRecyclerView.visibility = View.GONE
            fetchData()
            binding.swipeRefreshView.isRefreshing = false
        }
    }


}