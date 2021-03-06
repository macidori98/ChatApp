package com.example.chatapp.util;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.chatapp.MainActivity;
import com.example.chatapp.R;
import com.example.chatapp.home.HomeFragment;
import com.example.chatapp.registration.RegistrationFragment;

public class FragmentNavigation extends Fragment {
    private static final Handler handler = new Handler();
    private static int iMainActivityFragmentContainer;
    private static boolean bDoubleBackToExitPressedOnce = false;
    private static FragmentNavigation fragmentNavigationInstance;
    private static FragmentManager fragmentManager;
    private static FragmentTransaction fragmentTransaction;

    public static FragmentNavigation getInstance(Context context) {
        if (fragmentNavigationInstance == null) {
            iMainActivityFragmentContainer = R.id.fragment_content;
            fragmentNavigationInstance = new FragmentNavigation();
            fragmentManager = ((MainActivity) context).getSupportFragmentManager();
        }

        return fragmentNavigationInstance;
    }

    private void addFragment(Fragment fragment, int container) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container, fragment, fragment.getTag());
        fragmentTransaction.addToBackStack(null);
        try {
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void replaceFragment(Fragment fragment, int container) {
        fragmentTransaction = fragmentManager.beginTransaction();
        Fragment topFragment = fragmentManager.findFragmentById(container);
        if (topFragment == null) {
            addFragment(fragment, container);
        } else {
            fragmentTransaction.replace(container, fragment, fragment.getTag());
            fragmentTransaction.addToBackStack(null);
            try {
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Fragment getCurrentFragment(int container) {
        return fragmentManager.findFragmentById(container);
    }

    public void onBackPressed(MainActivity activity) {
        // If Home page is open: double press exit:
        /*if (getCurrentFragment(iMainActivityFragmentContainer) instanceof RegistrationFragment) {
            doublePressExit(activity);
            return;
        }

        /*if (getCurrentFragment(iMainActivityFragmentContainer) instanceof HomeFragment) {
            doublePressExit(activity);
            return;
        }*/

        if (getCurrentFragment(iMainActivityFragmentContainer) instanceof RegistrationFragment) {
            popBackStack();
            return;
        }

        if (getCurrentFragment(iMainActivityFragmentContainer) instanceof HomeFragment) {
            doublePressExit(activity);
            return;
        }

        /*if (getCurrentFragment(iMainActivityFragmentContainer) instanceof WorkoutDetailsFragment) {
            popBackStack();
            return;
        }
*/
        //Other cases:
        activity.moveTaskToBack(true);
    }

    private void doublePressExit(MainActivity activity) {
        if (bDoubleBackToExitPressedOnce) {
            bDoubleBackToExitPressedOnce = false;
            activity.moveTaskToBack(true);
            return;
        }

        bDoubleBackToExitPressedOnce = true;
        Toast.makeText(activity, R.string.back_button_press, Toast.LENGTH_SHORT).show();
        handler.postDelayed(() -> bDoubleBackToExitPressedOnce = false, 2000);
    }

    public void popBackStack() {
        fragmentManager.popBackStack();
    }
}
