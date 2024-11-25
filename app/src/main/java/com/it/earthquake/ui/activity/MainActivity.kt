package com.it.earthquake.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.it.earthquake.databinding.ActivityMainBinding
import com.it.earthquake.ui.adapter.EarthquakeListAdapter
import com.it.earthquake.utils.Constants
import com.it.earthquake.viewmodel.EarthquakeListViewModel


/**
 * Main activity
 *
 * @constructor Create empty Main activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mViewModel: EarthquakeListViewModel
    private lateinit var mAdapter: EarthquakeListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        mBinding.earthquakeRecyclerView.layoutManager = LinearLayoutManager(this)
        mViewModel = ViewModelProvider.create(this)[EarthquakeListViewModel::class]
        mAdapter = EarthquakeListAdapter(arrayListOf()) { earthquake ->
            val intent = Intent(this, EarthquakeMapActivity::class.java)
            intent.putExtra(Constants.EXTRA_ITEM, earthquake)
            startActivity(intent)
        }
        observeLiveData()
        mBinding.earthquakeRecyclerView.adapter = mAdapter
        mViewModel.listGunceleSwipe(mBinding)
        mViewModel.fetchData()
    }

    private fun observeLiveData() {
        mViewModel.mEarthquakeFeature.observe(this, Observer { earthquakes ->
            earthquakes?.let {
                mBinding.earthquakeRecyclerView.visibility = View.VISIBLE
                mBinding.earthquakeTv.visibility = View.GONE
                mAdapter.updateEarthquakes(earthquakes)
            }
        })

        mViewModel.mErrorMessage.observe(this, Observer { hataMsg ->
            hataMsg?.let {
                if (hataMsg) {
                    mBinding.earthquakeTv.visibility = View.VISIBLE
                } else {
                    mBinding.earthquakeProgressBar.visibility = View.GONE
                }
            }
        })

        mViewModel.mProgressStatus.observe(this, Observer { progressStatus ->
            progressStatus?.let {
                if (progressStatus) {
                    mBinding.earthquakeRecyclerView.visibility = View.GONE
                    mBinding.earthquakeProgressBar.visibility = View.VISIBLE
                } else {
                    mBinding.earthquakeRecyclerView.visibility = View.VISIBLE
                    mBinding.earthquakeProgressBar.visibility = View.GONE
                }
            }
        })
    }
}