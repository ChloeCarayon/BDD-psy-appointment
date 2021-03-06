package UI;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public final class CreatRdv_GUI extends Default_Page implements ActionListener  {
    private JLabel DateLabel = new JLabel("Date");
    private JLabel HeureLabel = new JLabel("Heure");
    private JLabel Patient1Label = new JLabel("Patient 1");
    private JLabel Patient2Label = new JLabel("Patient 2");
    private JLabel Patient3Label = new JLabel("Patient 3");
    private JLabel PrixLabel = new JLabel("Prix (euros)");
    private JLabel PaymentLabel = new JLabel("Payement");
    private String [] ListPayment = {"CB", "Cheques", "Espece"};

    private  JTextField PrixField = new JTextField();
    private JLabel DateChoose;
    
    private JComboBox<String> Patient1ComboBox; 
    private JComboBox<String> Patient2ComboBox;
    private JComboBox<String> Patient3ComboBox;
    
    private JComboBox<String> HeureComboBox;
    private JComboBox<String> PaymentComboBox = new JComboBox<>(ListPayment);
    private JButton CreateButton = new JButton("Creer");
   

    public CreatRdv_GUI(String date) throws SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try {
            sdf.parse(date);
        } catch (ParseException e) { }
        DateChoose = new JLabel(date);
        SetListPatient();
        SetListHeure();
        createWindow("Creation d'un RDV",500, 100, 380, 500);
        setLocationAndSize();
        addComponentsToFrame();
        this.setVisible(true);
        if (!mySystem.mariaconnexion.Datecheck(date,-1)){
            JOptionPane.showMessageDialog(null, "Vous travaillez 10h a  ce jour.\nVeuillez selectionner une autre date");
            this.dispose();
            new RDVpsy_GUI();
        }
    }

    protected void setLocationAndSize() {   // Log in
        DateLabel.setBounds(30, 10, 70, 70);
        HeureLabel.setBounds(30, 60, 70, 70);
        Patient1Label.setBounds(30, 110, 70, 70);
        Patient2Label.setBounds(30, 160, 70, 70);
        Patient3Label.setBounds(30, 210, 70, 70);
        PrixLabel.setBounds(30, 260, 70, 70);
        PaymentLabel.setBounds(30, 310, 70, 70);

        DateChoose.setBounds(150, 35, 200, 20);
        HeureComboBox.setBounds(150, 80, 200, 20);
        Patient1ComboBox. setBounds(150, 135, 200, 20);
        Patient2ComboBox. setBounds(150, 185, 200, 20);
        Patient3ComboBox. setBounds(150, 235, 200, 20);
        PrixField.setBounds(150, 285, 200, 23);
        PaymentComboBox.setBounds(150, 330, 200, 23);
        

        CreateButton.setBounds(20, 400, 100, 23);
        exitButton.setBounds(250, 400, 100, 23);
        backButton.setBounds(130, 400, 100, 23);
    }

    protected void addComponentsToFrame() {   //Adding components to Frame
        this.add(DateLabel);
        this.add(HeureLabel);
        this.add(Patient1Label);
        this.add(Patient2Label);
        this.add(Patient3Label);
        this.add(PrixLabel);
        this.add(PaymentLabel);
        this.add(DateChoose);
        this.add(HeureComboBox);
        this.add(Patient1ComboBox);
        this.add(Patient2ComboBox);
        this.add(Patient3ComboBox);
        this.add(PrixField);
        this.add(PaymentComboBox);
        this.add(CreateButton);
        this.add(exitButton);
        this.add(backButton);

        CreateButton.addActionListener(this);
        exitButton.addActionListener(this);
        backButton.addActionListener(this);
    }

    private void SetListPatient(){
    	   // try {
    	        String[] ListPatient = new String[mySystem.patients.size()+1];
    	        ListPatient[0] = "null";
    	   
    	        for(int cnt=0;cnt<mySystem.patients.size();cnt++)
    	            ListPatient[cnt+1] = (mySystem.patients.get(cnt).getNom() + " . "+ mySystem.patients.get(cnt).getPrenom() );
    	        
    	        Patient1ComboBox = new JComboBox<>(ListPatient);
    	        Patient2ComboBox = new JComboBox<>(ListPatient);
    	        Patient3ComboBox = new JComboBox<>(ListPatient);
    	}


    private void SetListHeure(){
        String[] Heure = {"8h00","8h30", "9h00","9h30", "10h00","10h30", "11h00","11h30", "12h00","12h30", "13h00","13h30", "14h00",
                "14h30", "15h00","15h30", "16h00","16h30", "17h00","17h30", "18h00","18h30", "19h00","19h30", "20h00"};
        List<String> list = new ArrayList<String>(Arrays.asList(Heure));
        for(int cnt=0;cnt<mySystem.rdvListe.size();cnt++) {
            if (DateChoose.getText().equals(String.valueOf((mySystem.rdvListe.get(cnt).getDate() )) ))
                list.remove(mySystem.rdvListe.get(cnt).getHeure()); 
        }
        String[] Dispo = new String[0];
        Dispo = list.toArray(Dispo);
        HeureComboBox = new JComboBox<>(Dispo);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==CreateButton){
            if (Patient1ComboBox.getSelectedItem().toString().equals("null") && Patient2ComboBox.getSelectedItem().toString().equals("null")
                    && Patient3ComboBox.getSelectedItem().toString().equals("null") )
                JOptionPane.showMessageDialog(null, "Veuillez rentrer au moins 1 patient.");
            else {
                if ( (Patient1ComboBox.getSelectedItem().toString().equals(Patient2ComboBox.getSelectedItem().toString()) && !Patient1ComboBox.getSelectedItem().toString().equals("null"))
                        || (Patient1ComboBox.getSelectedItem().toString().equals(Patient3ComboBox.getSelectedItem().toString()) && !Patient3ComboBox.getSelectedItem().toString().equals("null"))
                        ||  (Patient2ComboBox.getSelectedItem().toString().equals(Patient3ComboBox.getSelectedItem().toString()) && !Patient2ComboBox.getSelectedItem().toString().equals("null")))
                    JOptionPane.showMessageDialog(null, "Vous avez rentre plusieurs fois le meme patient.");
                else {
                    if (PrixField.getText().equals("")) JOptionPane.showMessageDialog(null, "Veuillez rentrer un prix.");
                    else {
                        try {
                            mySystem.mariaconnexion.FillRDV(DateChoose.getText(),HeureComboBox.getSelectedItem().toString(),Float.parseFloat(PrixField.getText()),PaymentComboBox.getSelectedItem().toString(),
                                    Patient1ComboBox.getSelectedItem().toString(),Patient2ComboBox.getSelectedItem().toString(),Patient3ComboBox.getSelectedItem().toString(),null);
                            this.dispose();
                            new RDVpsy_GUI();
                        }
                        catch (NumberFormatException nfe) {
                            JOptionPane.showMessageDialog(null, "Veuillez entrer des chiffres pour le prix.");
                            PrixField.setBorder(BorderFactory.createLineBorder(Color.red));
                        }
                        catch (SQLException fe) {
                            JOptionPane.showMessageDialog(null, "Veuillez rentrer un patient pour patient 1.");
                        }
                    }
                }
            }
        }
        else if(e.getSource()==backButton){
            this.dispose();
            new RDVpsy_GUI();
        }
        else if(e.getSource()==exitButton){
            this.dispose();
            new Psy_GUI();
        }
    }

}