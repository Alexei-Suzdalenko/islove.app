package islove.app.assets.adapter
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import islove.app.R
import islove.app.assets.fragments.ChatFragment
import islove.app.assets.fragments.ContactsFragment
import islove.app.assets.fragments.GroupsFragment

class TabsPagerAdapter(c: Context, manager: FragmentManager): FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    val  fragments: ArrayList<Fragment>
    val titles: ArrayList<String>

    init {
          fragments = ArrayList(); titles = ArrayList();
          fragments.add(ChatFragment()); titles.add( c.resources.getString(R.string.chats))
          fragments.add(GroupsFragment()); titles.add( c.resources.getString(R.string.groups))
         fragments.add(ContactsFragment()); titles.add( c.resources.getString(R.string.contacts))
    }

    override fun getCount(): Int { return fragments.size; }
    override fun getItem(position: Int): Fragment { return fragments[position]; }
    override fun getPageTitle(i: Int): CharSequence { return titles[i]; }
}



/*
viewPagerAdapter.addFragment(ChatFragment(), resources.getString(R.string.chats))
             viewPagerAdapter.addFragment(SearchFragment(), resources.getString(R.string.search))
             viewPagerAdapter.addFragment(SettingsFragment(), resources.getString(R.string.settings))
 */

/*
    fun addFragment(fragment: Fragment, title: String){
        fragments.add(fragment); titles.add(title)
    }
*/