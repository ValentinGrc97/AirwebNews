package fr.Airweb.news.ui.newsDescription

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import fr.Airweb.news.R
import kotlinx.android.synthetic.main.fragment_news_card.tv_title
import kotlinx.android.synthetic.main.news_description_fragment.*

class NewsDescriptionFragment: Fragment() {

    private lateinit var newsDescriptionViewModel: NewsDescriptionViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val factory = NewsDescriptionViewModelFactory(requireArguments().getInt("id"), requireActivity().application)
        newsDescriptionViewModel = ViewModelProvider(this, factory).get(NewsDescriptionViewModel::class.java)
        return inflater.inflate(R.layout.news_description_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        newsDescriptionViewModel.getNews().observe(viewLifecycleOwner, Observer { news ->
            if(news != null) {
                tv_title.text = news.title
                Picasso.with(context).load(news.picture).into(iv_picture)
                tv_description.text = news.content
            }
        })
    }
}