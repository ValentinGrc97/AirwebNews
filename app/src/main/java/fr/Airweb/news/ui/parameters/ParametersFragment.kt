package fr.Airweb.news.ui.parameters

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.Airweb.news.R
import kotlinx.android.synthetic.main.parameters_fragment.*
import java.util.*

class ParametersFragment : Fragment() {

    private lateinit var parametersViewModel: ParametersViewModel
    private lateinit var sharedPreferences: SharedPreferences
    private var sortType = ""
    private var orderBy = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        parametersViewModel = ViewModelProvider(this).get(ParametersViewModel::class.java)
        return inflater.inflate(R.layout.parameters_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences("sharedpreferences", 0)
        sortType = sharedPreferences.getString("sort_type", "news").toString()
        when (sortType) {
            requireContext().resources.getString(R.string.news).decapitalize() -> rg_type_sort.check(rb_news.id)
            requireContext().resources.getString(R.string.hot).decapitalize() -> rg_type_sort.check(rb_hot.id)
            requireContext().resources.getString(R.string.actualite).decapitalize() -> rg_type_sort.check(rb_actualite.id)
        }
        orderBy = sharedPreferences.getString("order_by", "date").toString()
        when (orderBy) {
            requireContext().resources.getString(R.string.date).decapitalize() -> rg_order_by.check(rb_date.id)
            requireContext().resources.getString(R.string.title).decapitalize() -> rg_order_by.check(rb_title.id)
        }
        rg_type_sort.setOnCheckedChangeListener { _, checkedId ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            when (checkedId) {
                rb_news.id -> editor.putString("sort_type", rb_news.text.toString().decapitalize())
                rb_hot.id -> editor.putString("sort_type", rb_hot.text.toString().decapitalize())
                rb_actualite.id -> editor.putString("sort_type", rb_actualite.text.toString().decapitalize())
            }
            editor.apply()
        }
        rg_order_by.setOnCheckedChangeListener { _, checkedId ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            when (checkedId) {
                rb_date.id -> editor.putString("order_by", rb_date.text.toString().decapitalize())
                rb_title.id -> editor.putString("order_by", rb_title.text.toString().decapitalize())
            }
            editor.apply()
        }
    }
}