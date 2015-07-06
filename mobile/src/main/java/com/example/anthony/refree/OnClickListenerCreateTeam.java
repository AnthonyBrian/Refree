package com.example.anthony.refree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Anthony on 16.06.2015.
 */
public class OnClickListenerCreateTeam implements View.OnClickListener {

    @Override
    public void onClick(View view) {

        final Context context = view.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.team_input_form, null, false);

        final EditText editTeamName = (EditText) formElementsView.findViewById(R.id.editTeamName);



        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Mannschaft erstellen")
                .setPositiveButton("Hinzufuegen",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int id) {

                                String TeamFirstname = editTeamName.getText().toString();
                                ObjectTeam objectTeam = new ObjectTeam();
                                objectTeam.teamname = TeamFirstname;

                                boolean createSuccessful = new TableControllerTeam(context).create(objectTeam);

                                if (createSuccessful) {
                                    Toast.makeText(context, "Mannschaftsname wurde gespeichert.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Mannschaftsname konnte nicht gespeichert werden.", Toast.LENGTH_SHORT).show();
                                }

                                ((OptionActivity)context).countRecords();
                                ((OptionActivity) context).readRecords();

                            }

                        }).show();


    }
}
