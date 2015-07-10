package com.example.anthony.refree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class OnLongClickListenerTeamRecord implements View.OnLongClickListener {

    Context context;

    @Override
    public boolean onLongClick(View view) {


        final String id;

        context = view.getContext();
        id = view.getTag().toString();

        final CharSequence[] items = { "Bearbeiten", "Loeschen" };

        new AlertDialog.Builder(context).setTitle("Team Datensatz")
                .setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {

                        if (item == 0) {
                            editRecord(Integer.parseInt(id));
                        }

                        else if (item == 1) {

                            boolean deleteSuccessful = new TableControllerTeam(context).delete(id);

                            if (deleteSuccessful){
                                Toast.makeText(context, "Team wurde geloescht", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context, "Fehler beim Loeschen.", Toast.LENGTH_SHORT).show();
                            }

                            ((OptionActivity) context).countRecords();
                            ((OptionActivity) context).readRecords();

                        }

                        dialog.dismiss();

                    }
                }).show();

        return false;



    }
    public void editRecord(final int TeamId) {

        final TableControllerTeam tableControllerTeam = new TableControllerTeam(context);
        ObjectTeam objectTeam = tableControllerTeam.readSingleRecord(TeamId);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View formElementsView = inflater.inflate(R.layout.team_input_form, null, false);

        final EditText editTextTeamname = (EditText) formElementsView.findViewById(R.id.editTeamName);

        editTextTeamname.setText(objectTeam.teamname);

        new AlertDialog.Builder(context)
                .setView(formElementsView)
                .setTitle("Editieren")
                .setPositiveButton("Speichern",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                ObjectTeam objectTeam = new ObjectTeam();
                                objectTeam.id = TeamId;
                                objectTeam.teamname = editTextTeamname.getText().toString();

                                boolean updateSuccessful = tableControllerTeam.update(objectTeam);

                                if(updateSuccessful){
                                    Toast.makeText(context, "Team wurde erfolgreich veraendert.", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(context, "Fehler beim Aendern.", Toast.LENGTH_SHORT).show();
                                }

                                ((OptionActivity)OnLongClickListenerTeamRecord.this.context).countRecords();
                                ((OptionActivity)OnLongClickListenerTeamRecord.this.context).readRecords();

                                dialog.cancel();
                            }

                        }).show();


    }

}
