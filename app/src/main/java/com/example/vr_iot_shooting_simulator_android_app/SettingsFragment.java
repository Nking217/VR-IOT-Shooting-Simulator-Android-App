package com.example.vr_iot_shooting_simulator_android_app;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


public class SettingsFragment extends Fragment {

    private Switch switchDarkMode;
    private AppCompatDelegate delegate;

    private Button button;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_settings, container, false);

            // Set up the switch and its listener
            switchDarkMode = view.findViewById(R.id.switch_dark_mode);
            switchDarkMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // Toggle between light and dark mode
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                    // Recreate the activity for the changes to take effect
                    getActivity().setTheme(R.style.AppTheme_Dark);
//                    requireActivity().recreate();
                }
            });

            // Get the AppCompatDelegate instance
            delegate = ((AppCompatActivity) requireActivity()).getDelegate();

            Button logoutButton = view.findViewById(R.id.logout); // Replace R.id.logout_button with the actual ID of your logout button

            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    logout();
                }
            });


            return view;
        }

        @Override
        public void onResume() {
            super.onResume();
            // Set the initial state of the switch based on the current mode
            switchDarkMode.setChecked((AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES));
        }

    // Inside your fragment, add the following code to log out the user and navigate back to the login activity
    private void logout() {
        // Clear the user session data
        // For example, clear SharedPreferences, clear user data from database, etc.

        // Create an intent to launch the login activity
        Intent intent = new Intent(getActivity(), LoginScreen.class);
        // Clear the activity stack and start the login activity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        Toast.makeText(getContext(), "User has logged out", Toast.LENGTH_SHORT).show();
        // Finish the current fragment to prevent the user from navigating back to it after logout
        getActivity().finish();
    }


}