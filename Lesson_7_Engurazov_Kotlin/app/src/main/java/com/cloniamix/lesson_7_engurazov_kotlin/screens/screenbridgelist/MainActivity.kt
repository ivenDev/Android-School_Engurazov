package com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgelist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloniamix.lesson_7_engurazov_kotlin.MyListener
import com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgelist.adapter.BridgesAdapter
import com.cloniamix.lesson_7_engurazov_kotlin.R
import com.cloniamix.lesson_7_engurazov_kotlin.Utils
import com.cloniamix.lesson_7_engurazov_kotlin.network.BridgeApiService
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridge
import com.cloniamix.lesson_7_engurazov_kotlin.pojo.Bridges
import com.cloniamix.lesson_7_engurazov_kotlin.screens.screenbridgedetails.BridgeDetailsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MyListener {

    companion object {
        private const val FLAG_DATA = 1
        private const val FLAG_ERROR = 2
        private const val FLAG_PROGRESS = 3

        /*fun createStartIntent(context: Context, bridge: Bridge): Intent {
            return Intent(context, MainActivity::class.java)
        }*/
    }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipeRefresh.setOnRefreshListener { getBridgeListFromApi() }

        //fixme: не работает listener
        imageViewRefresh.setOnClickListener{ refreshUi() }

        showView(FLAG_PROGRESS)
        getBridgeListFromApi()

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }

    override fun onMyClick(bridge: Bridge) {
        startActivity(BridgeDetailsActivity.createStartIntent(this, bridge))
    }

    private fun getBridgeListFromApi(){
        disposable = BridgeApiService.getClient.getBridges()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(this::updateUi, this::onError)
    }

    private fun updateUi(bridges: Bridges){
        showView(FLAG_DATA)
        swipeRefresh.isRefreshing = false
        recyclerViewBridgeList.layoutManager = LinearLayoutManager(this)
        recyclerViewBridgeList.adapter = BridgesAdapter(bridges.objects, this)
    }

    private fun onError(t: Throwable?){
        showView(FLAG_ERROR)
        swipeRefresh.isRefreshing = false
        Utils.log(t.toString())

    }

    private fun refreshUi(){
        showView(FLAG_PROGRESS)
        getBridgeListFromApi()
    }

    private fun showView(flag: Int){

        when(flag){
            FLAG_DATA -> {
                Utils.setViewVisible(progressBar, false)
                Utils.setViewVisible(recyclerViewBridgeList, true)
                Utils.setViewVisible(layoutError, false)
            }

            FLAG_ERROR -> {
                Utils.setViewVisible(progressBar, false)
                Utils.setViewVisible(recyclerViewBridgeList, false)
                Utils.setViewVisible(layoutError, true)
            }

            FLAG_PROGRESS -> {
                Utils.setViewVisible(progressBar, true)
                Utils.setViewVisible(recyclerViewBridgeList, false)
                Utils.setViewVisible(layoutError, false)
            }
        }
    }
}
