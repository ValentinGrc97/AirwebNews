package fr.Airweb.news.ui.contact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import fr.Airweb.news.R
import kotlinx.android.synthetic.main.contact_fragment.*


class ContactFragment: Fragment() {

    private lateinit var contactViewModel: ContactViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        contactViewModel = ViewModelProvider(this).get(ContactViewModel::class.java)
        return inflater.inflate(R.layout.contact_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_address.setOnClickListener {
            val gmmIntentUri = Uri.parse("geo:48.8407802714409,2.219731884465444")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(requireContext().packageManager)?.let {
                startActivity(mapIntent)
            }
        }

        tv_mail.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", requireContext().resources.getString(R.string.mail), null
                )
            )
            startActivity(Intent.createChooser(intent, "Choose an Email client :"))
        }

        tv_phone.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL,
                Uri.parse("tel:" + requireContext().resources.getString(R.string.phone)))
            startActivity(intent)
        }
    }
}