package de.classicgameshe.classicgameshe.fm;

/**
 * Created by mastereder on 15.10.15.
 */
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.classicgameshe.classicgameshe.MainActivity;
import de.classicgameshe.classicgameshe.R;
import de.classicgameshe.classicgameshe.adapter.TicTacToeDataBaseAdapter;
import de.classicgameshe.classicgameshe.support.PercentView;

public class statisticFragment extends Fragment {

    View rootview;
    PercentView percentView;

    public static statisticFragment newInstance() {
        statisticFragment fragment = new statisticFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.statistic_layout, container, false);
        return rootview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        percentView = (PercentView) view.findViewById(R.id.percentview);
        TextView xWinnerTV = (TextView) view.findViewById(R.id.statistic_x_winner_tv);
        TextView oWinnerTV = (TextView) view.findViewById(R.id.statistic_o_winner_tv);

        String userID = ((MainActivity) getActivity()).loadUserID();
        TicTacToeDataBaseAdapter ticTacToeDataBaseAdapter = new TicTacToeDataBaseAdapter(getActivity());

        int xWins = ticTacToeDataBaseAdapter.getXWins(userID);
        int oWins = ticTacToeDataBaseAdapter.getOWins(userID);
        xWinnerTV.setText(String.format(getString(R.string.statistic_x_winner_text),
                xWins));
        oWinnerTV.setText(String.format(getString(R.string.statistic_o_winner_text),
                oWins));

        //Prozent berechnen
        float onePercent = 100 /(float)(xWins+oWins);
        float setPercentage = onePercent*oWins;
        percentView.setPercentage(setPercentage);
    }
}



//git status                  -> anzeige alle veränderten files
//git add <pfad der datei>    -> für jedes file das commit werdn soll
//git commit -m "Kommentar"
//git push                    -> hochladen
//git pull                    -> herunterladen
