package fr.Airweb.news.ui.newsList

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
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
    private var listener: OnNewsListFragmentInteractionListener? = null
    private lateinit var sharedPreferences: SharedPreferences
    private var sortType = "news"
    private var orderBy = "date"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        newsListViewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)
        return inflater.inflate(R.layout.news_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        swipe_container_news.setOnRefreshListener(this)
        et_search.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {}

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                search(p0.toString())
            }
        })
    }

    override fun onRefresh() {
        displayData()
    }

    override fun onResume() {
        super.onResume()
        displayData()
    }

    private fun displayData() {
        swipe_container_news.isRefreshing = true
        val alertDialog = AlertDialog.Builder(context).create()
        alertDialog.setTitle("Chargement")
        alertDialog.setMessage("Veuillez patienter")
        alertDialog.show()
        sharedPreferences =  requireContext().getSharedPreferences("sharedpreferences", 0)
        sortType = sharedPreferences.getString("sort_type", "news").toString()
        orderBy = sharedPreferences.getString("order_by", "date").toString()
        newsListViewModel.getListNewsByTypeAndOrderBy(sortType, orderBy)
            .observe(viewLifecycleOwner, Observer { news ->
                if (news.isNotEmpty()) updateAdapter(news)
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

    private fun search(text: String) {
        sortType = sharedPreferences.getString("sort_type", "news").toString()
        newsListViewModel.getListNewsByName(text, sortType)
            .observe(viewLifecycleOwner, Observer { news ->
                if (news.isNotEmpty()) updateAdapter(news)
            })
    }

    override fun onAttach(context: Context) {

        super.onAttach(context)
        if (context is OnNewsListFragmentInteractionListener) {
            listener = context
        }
        else {
            throw RuntimeException("$context must implement OnNewsListFragmentInteractionListener")
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