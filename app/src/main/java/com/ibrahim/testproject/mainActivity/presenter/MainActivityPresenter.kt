package com.ibrahim.testproject.mainActivity.presenter


import android.content.Context
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ibrahim.testproject.adapters.DataAdapter
import com.ibrahim.testproject.netWork.RetroWeb
import com.ibrahim.testproject.netWork.models.DataModel
import com.ibrahim.testproject.netWork.services.ServicesAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.view.*

class MainActivityPresenter : MainActivityViewPresenter {

    private val composite = CompositeDisposable()

    var pageNumbr = 1
    var nextUrl = "users/JakeWharton/repos?page=$pageNumbr&per_page=15"

    internal var visibleItemCount: Int = 0
    internal var totalItemCount: Int = 0
    internal var pastVisiblesItems: Int = 0
    internal var loading: Boolean? = false
    lateinit var service: ServicesAPI


    override fun getData(context: Context, view: View) {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        view.rvData.layoutManager = linearLayoutManager

        val retrofit = RetroWeb.getClient()
        service = retrofit!!.create(ServicesAPI::class.java)
        Paginate(context,view.rvData,linearLayoutManager)
        composite.add(service.data(nextUrl).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnError { t: Throwable -> Log.e("th", t.toString() + "GOOD") }
            .subscribe { DataModel -> displayData(DataModel, context, view.rvData) })
        Log.e("data", "ffffffff")
        Log.e("composite", composite.toString())


    }

    private fun displayData(data: List<DataModel>?, context: Context, recyclerView: RecyclerView) {
        val dataAdapter = DataAdapter(context)
        dataAdapter.add(data!!)
        dataAdapter.notifyDataSetChanged()
        recyclerView.adapter = dataAdapter
    }
    fun Paginate(context: Context, recyclerView: RecyclerView, linearLayoutManager:LinearLayoutManager){
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                Log.e("dy", dy.toString())
                Log.e("dyy", recyclerView.computeVerticalScrollOffset().toString())
                if (dy > 0) {
                    visibleItemCount = linearLayoutManager.childCount
                    totalItemCount = linearLayoutManager.itemCount
                    pastVisiblesItems =
                        (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                    //                    Log.e("LOOOAAAD", nextUrl);

                    Log.e("ccc", (visibleItemCount + pastVisiblesItems).toString())
                    Log.e("cccccccc", (totalItemCount).toString())
                        if (visibleItemCount + pastVisiblesItems >= totalItemCount) {
                            loading = false
                            Log.e("...", "Last Item Wow !")
                            Log.e("nextUrl", nextUrl)
                            pageNumbr++
                            Log.e("nextUrlAfter", nextUrl)
                            composite.add(service.data(nextUrl).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .doOnError { t: Throwable -> Log.e("th", t.toString() + "GOOD") }
                                .subscribe { DataModel -> displayData(DataModel, context, recyclerView) })







                    }
                }
            }
        })
    }

}

