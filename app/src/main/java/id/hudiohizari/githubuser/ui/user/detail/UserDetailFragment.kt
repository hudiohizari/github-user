package id.hudiohizari.githubuser.ui.user.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import id.hudiohizari.githubuser.R

@AndroidEntryPoint
class UserDetailFragment : Fragment() {

    val args: UserDetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_user_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = args.username
        Log.e("UserDetailFragmentArgs", "username: $username")

        val id = args.id
        Log.e("UserDetailFragmentArgs", "id: $id")
    }

}