package com.votelimes.whacamole.fragment;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.votelimes.whacamole.R;
import com.votelimes.whacamole.databinding.FragmentHomeBinding;
import com.votelimes.whacamole.databinding.FragmentScoreBinding;
import com.votelimes.whacamole.viewmodel.HomeViewModel;
import com.votelimes.whacamole.viewmodel.ScoreViewModel;

public class HomeFragment extends Fragment {

    private HomeViewModel viewModel;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        binding.setVm(viewModel);
        binding.executePendingBindings();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton startGameButton = view.findViewById(R.id.start_game);

        try {
            startGameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onStartGame();
                }
            });
        } catch (NullPointerException e){
            e.printStackTrace();
            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle("An error acquired")
                    .setMessage("A critical error has occurred. Contact support. Error code 1.")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .show();
        }

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        else {
            getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public void finish(){
        finish();
    }

    public void onStartGame(){
        NavController nc = Navigation.findNavController(getView());
        NavDirections direction = HomeFragmentDirections.actionHomeFragmentToGameFragment();
        nc.navigate(direction);
    }
}