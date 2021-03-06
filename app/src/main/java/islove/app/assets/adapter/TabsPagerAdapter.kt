package islove.app.assets.adapter
import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import islove.app.R
import islove.app.assets.fragments.SearchUsersFragment
import islove.app.assets.fragments.ContactsFragment

class TabsPagerAdapter(c: Context, manager: FragmentManager): FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val  fragments: ArrayList<Fragment> = ArrayList()
    private val titles: ArrayList<String> = ArrayList()

    init {
          fragments.add(SearchUsersFragment()); titles.add( c.resources.getString(R.string.listUsers))
          // fragments.add(GroupsFragment()); titles.add( c.resources.getString(R.string.groups))
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