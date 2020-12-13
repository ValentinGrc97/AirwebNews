package fr.Airweb.news.ui.newsList

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import fr.Airweb.news.R
import fr.Airweb.news.adapter.NewsRecyclerViewAdapter
import fr.Airweb.news.database.news.News
import kotlinx.android.synthetic.main.news_list_fragment.*


class NewsListFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var newsListViewModel: NewsListViewModel
    private val owner = this
    private var listener: OnNewsListFragmentInteractionListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        newsListViewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_container_news.setOnRefreshListener(this)
        swipe_container_news.setProgressViewOffset(true, 0, 180)
        swipe_container_news.isRefreshing = true
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Chargement")
        alertDialog.setMessage("Veuillez patienter")
        alertDialog.show()
        newsListViewModel.getNews().observe(viewLifecycleOwner, Observer {news ->
            if (news.isNotEmpty()) updateAdapter(news)
            swipe_container_news.isRefreshing = false
            alertDialog.dismiss()
        })
    }

    override fun onRefresh() {
        swipe_container_news.isRefreshing = true
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Chargement")
        alertDialog.setMessage("Veuillez patienter")
        alertDialog.show()
        newsListViewModel.requestNews()
        newsListViewModel.getNews().observe(owner, Observer { news ->
            updateAdapter(news)
            swipe_container_news.isRefreshing = false
            alertDialog.dismiss()
        })
    }

    private fun updateAdapter(news: List<News>) {

        if (rv_list_news is RecyclerView) {
            with(rv_list_news) {
                layoutManager = LinearLayoutManager(context)
                adapter = NewsRecyclerViewAdapter(news, listener)
            }
        }
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is OnNewsListFragmentInteractionListener) {
            listener = context
        }
        else {
            throw RuntimeException("$context must implement OnCampaignListFragmentInteractionListener")
        }
    }

    override fun onDetach() {

        super.onDetach()
        listener = null
    }

    interface OnNewsListFragmentInteractionListener {
        fun onNewsListFragmentInteraction(item: News)
    }

}