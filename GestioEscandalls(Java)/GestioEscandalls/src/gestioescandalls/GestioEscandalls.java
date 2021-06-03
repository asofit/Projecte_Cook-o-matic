package gestioescandalls;

import gestioescandalls.Model.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class GestioEscandalls {

    // Elements de UI
    private JFrame mainFrame;
    private JList llistaPlats;
    private DefaultListModel modelLlistaPlats = new DefaultListModel();
    private JComboBox<Categoria> cboCategories;
    private ButtonGroup btnGrpDisponibilitat;
    private JRadioButton btnAll;
    private JRadioButton btnSi;
    private JRadioButton btnNo;
    private JTextField cerca;
    private JButton btnCerca;
    private JButton btnEdit;
    private EditFrame edt;
    
    private Categoria selectedCategoria;
    private int selectedDisponibilitat;
    private String selectedText;
    private Plat selectedPlat;
    
    // Llistes
    private List<Plat> plats;
    private List<Categoria> categories;
    private List<Unitat> unitats;
    private List<Ingredient> ingredients;
    
    public GestioEscandalls(EntityManager em) {
        refrescaLlistes(em);
        
        mainFrame = new JFrame("Gestió d'Escandalls");
        
        llistaPlats = new JList();
        llistaPlats.setCellRenderer(new platCellRenderer());
        llistaPlats.setModel(modelLlistaPlats);
        refreshListPlats();
        llistaPlats.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnEdit.setEnabled(!llistaPlats.isSelectionEmpty());
                selectedPlat = (Plat)llistaPlats.getSelectedValue();
            }
        });
        
        JScrollPane eastFrame = new JScrollPane(llistaPlats);
        Dimension listSize = new Dimension(400, 100);
        eastFrame.setSize(listSize);
        eastFrame.setMaximumSize(listSize);
        eastFrame.setPreferredSize(listSize);
        
        Border m1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
        Border m2 = BorderFactory.createEmptyBorder(0, 40, 5, 0);
        Border m3 = BorderFactory.createCompoundBorder(m2, m1);

        eastFrame.setBorder(m3);
        
        eastFrame.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        eastFrame.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        JPanel southFrame = new JPanel();
        
        JPanel northFrame = new JPanel();
        
        JPanel westFrame = new JPanel();
        westFrame.setLayout(new BoxLayout(westFrame, BoxLayout.Y_AXIS));
        
        JPanel northWestFrame = new JPanel();
        northWestFrame.setLayout(new BoxLayout(northWestFrame, BoxLayout.X_AXIS));
        JLabel lblCategories = new JLabel("Categories: ");
        JComboBox<Categoria> cboCategories = new JComboBox<Categoria>();
        selectedCategoria = null;
        cboCategories.addItem(null);
        for (int i = 0; i < categories.size(); i++) {
          cboCategories.addItem(categories.get(i));
        }
        cboCategories.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                JComboBox cb = (JComboBox)action.getSource();
                selectedCategoria = (Categoria)cb.getSelectedItem();
                refreshListPlats();
            }
        });
        northWestFrame.add(lblCategories);
        northWestFrame.add(cboCategories);
        
        JPanel centerWestFrame = new JPanel();
        centerWestFrame.setLayout(new BoxLayout(centerWestFrame, BoxLayout.X_AXIS));
        JLabel lblDisponibilitat = new JLabel("Disponibilitat: ");
        btnAll = new JRadioButton("Totes",false);
        btnSi = new JRadioButton("Sí",false);
        btnNo = new JRadioButton("No",false);
        
        btnAll.addItemListener(new ItemsRadioListener());
        btnSi.addItemListener(new ItemsRadioListener());
        btnNo.addItemListener(new ItemsRadioListener());
        
        btnGrpDisponibilitat = new ButtonGroup();
        btnGrpDisponibilitat.add(btnAll);
        btnGrpDisponibilitat.add(btnSi);
        btnGrpDisponibilitat.add(btnNo);
        
        centerWestFrame.add(lblDisponibilitat);
        centerWestFrame.add(btnAll);
        centerWestFrame.add(btnSi);
        centerWestFrame.add(btnNo);
        
        JPanel southWestFrame = new JPanel();
        southWestFrame.setLayout(new BoxLayout(southWestFrame, BoxLayout.X_AXIS));
        cerca = new JTextField();
        btnCerca = new JButton("Cerca");
        btnCerca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent action) {
                selectedText = cerca.getText();
                refreshListPlats();
            }
        });
        southWestFrame.add(cerca);
        southWestFrame.add(btnCerca);
        
        westFrame.add(northWestFrame, BorderLayout.NORTH);        
        westFrame.add(centerWestFrame, BorderLayout.CENTER);
        westFrame.add(southWestFrame, BorderLayout.SOUTH);
        
        btnEdit = new JButton("Edita Escandall");
        btnEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                edt = new EditFrame("Edició d'Escandall", selectedPlat, em);
                edt.setVisible(true);
            }
        });
        btnEdit.setEnabled(false);
        southFrame.add(btnEdit);
        
        mainFrame.add(northFrame,BorderLayout.NORTH);
        mainFrame.add(eastFrame,BorderLayout.EAST);
        mainFrame.add(westFrame,BorderLayout.WEST);
        mainFrame.add(southFrame,BorderLayout.SOUTH);
        
        mainFrame.pack();
        mainFrame.setVisible(true);
        Dimension mainFrameSize = new Dimension(700, 800);
        mainFrame.setSize(mainFrameSize);
        mainFrame.setResizable(false);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    // disponibilitat: 0 = Totes; 1 = Sí; 2 = No
    private void refreshListPlats() {
        modelLlistaPlats.clear();
        // Initialize the list with items
        if (selectedCategoria!= null || selectedDisponibilitat != 0 || (selectedText != null && selectedText.length() > 0)){
            int j = 0;
            for (int i = 0; i < plats.size(); i++) {
                Plat p = plats.get(i);
                if ((selectedCategoria == null || p.getCategoria().equals(selectedCategoria)) &&
                    (selectedDisponibilitat == 0 || ((selectedDisponibilitat == 1 && p.isDisponible()) || (selectedDisponibilitat == 2 && (p.isDisponible()==false)))) && 
                    ((selectedText == null || selectedText.length() <= 0) || p.getNom().contains(selectedText))){
                    modelLlistaPlats.add(j, p);
                    j++;
                }
            }
        }
        else{
            for (int i = 0; i < plats.size(); i++) {
                modelLlistaPlats.add(i, plats.get(i));
            }
        }
    }
    
    // Selects de BD
    private void refrescaLlistes(EntityManager em) {
        // Obtenim les llistes necessàries
        plats = getAllPlats(em);
        categories = getAllCategories(em);
        unitats = getAllUnitats(em);
        ingredients = getAllIngredients(em);
    }
    private List<Categoria> getAllCategories(EntityManager em) {
        String consulta = "select c from Categoria c";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }
    private List<Plat>getAllPlats(EntityManager em) {
        String consulta = "select p from Plat p";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }
    private List<Unitat> getAllUnitats(EntityManager em) {
        String consulta = "select u from Unitat u";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }
    private List<Ingredient> getAllIngredients(EntityManager em) {
        String consulta = "select i from Ingredient i";
        Query q = em.createQuery(consulta);
        return q.getResultList();
    }

    private class ItemsRadioListener implements ItemListener {

        public ItemsRadioListener() {
        }

        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED)
                {
                if (btnAll.isSelected())
                {
                    selectedDisponibilitat = 0;
                } else if (btnSi.isSelected())
                {
                    selectedDisponibilitat = 1;
                }else if (btnNo.isSelected())
                {
                    selectedDisponibilitat = 2;
                }
                refreshListPlats();
            }
        
        }
    }

    private static class platCellRenderer extends DefaultListCellRenderer {
        public Component getListCellRendererComponent( JList list, Object plat, int index, boolean isSelected, boolean cellHasFocus ) {
            Component c = super.getListCellRendererComponent( list, plat, index, isSelected, cellHasFocus );
            c.setBackground(((Plat)plat).getCategoria().getColor());
            return c;
        }
    }
    public class EditFrame extends JFrame {

        private JList llistaEscandall;
        private DefaultListModel modelLlistaEscandall = new DefaultListModel();
        private JPanel southFrame;
        private JDialog confirmacio;
        private JButton btnAddEscandall;
        private JLabel txtNom;
        private JLabel txtDesc;
        private JLabel txtPreu;
        private JLabel txtDisponibilitat;
        private JLabel txtCategoria;
        private JTextField txtQuantitat;

        private LiniaEscandall selectedEscandall = null;
        private Ingredient selectedIngredient = null;
        private Unitat selectedUnitat = null;
        private int selectedQuantitat = 0;
        private Plat currentPlat;

        public EditFrame(String nom, Plat platToEdit, EntityManager em) {
            super(nom);

            currentPlat = platToEdit;

            llistaEscandall = new JList();
            llistaEscandall.setModel(modelLlistaEscandall);
            refreshListEscandall();
            llistaEscandall.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    selectedEscandall = (LiniaEscandall)llistaEscandall.getSelectedValue();
                }
            });
            JPanel eastFrame = new JPanel();
            eastFrame.setLayout(new BoxLayout(eastFrame, BoxLayout.Y_AXIS));
            JScrollPane scrollFrame = new JScrollPane(llistaEscandall);
            Dimension listSize = new Dimension(400, 100);
            scrollFrame.setSize(listSize);
            scrollFrame.setMaximumSize(listSize);
            scrollFrame.setPreferredSize(listSize);

            Border m1 = BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1);
            Border m2 = BorderFactory.createEmptyBorder(0, 40, 5, 0);
            Border m3 = BorderFactory.createCompoundBorder(m2, m1);

            scrollFrame.setBorder(m3);

            confirmacio = new JDialog(this,true);

            Box bLabel = Box.createHorizontalBox();
            bLabel.add(new JLabel("Estàs segur/a d'eliminar la línia seleccionada?"));

            JButton bSi = new JButton("Sí");
            bSi.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (selectedEscandall != null){
                        if (LiniaEscandall.eliminaLiniaEscandall(em, selectedEscandall)){
                            refrescaLlistes(em);
                            em.refresh(currentPlat);
                            currentPlat = em.find(Plat.class, currentPlat.getId());
                            refreshPlat();
                            if (currentPlat == null){
                                edt.setVisible(false);
                            }
                        }
                        confirmacio.setVisible(false);
                    }
                }
            });
            JButton bCancel = new JButton("Cancel·la");
            bCancel.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    confirmacio.setVisible(false);
                }
            });
            Box bButtons = Box.createHorizontalBox();
            bButtons.add(bSi);
            bButtons.add(Box.createHorizontalStrut(20));
            bButtons.add(bCancel);
   
            Box b = Box.createVerticalBox();
            b.add(Box.createVerticalStrut(10));
            b.add(bLabel);
            b.add(Box.createVerticalStrut(20));
            b.add(bButtons);
            b.add(Box.createVerticalStrut(10));
            JPanel p = new JPanel();

            p.add(b);
            confirmacio.add(p);
            confirmacio.setTitle("Confirma eliminació línia?");
            confirmacio.pack();
            confirmacio.setResizable(false);
            confirmacio.setLocationRelativeTo(this);
            confirmacio.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            confirmacio.setVisible(false);

            JPanel buttonsPanel = new JPanel();
            JButton btnDelEscandall = new JButton("Elimina");
            btnDelEscandall.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    confirmacio.setVisible(true);
                }
            });
            btnAddEscandall = new JButton("Afegeix");
            btnAddEscandall.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    if (selectedIngredient != null && 
                        selectedUnitat != null && 
                        selectedQuantitat> 0){
                        LiniaEscandall le = new LiniaEscandall();
                        le.setIngredient(selectedIngredient);
                        le.setUnitat(selectedUnitat);
                        le.setQuantitat(selectedQuantitat);
                        le.setPlat(currentPlat);
                        int id = 0;
                        List<LiniaEscandall> escandall =  currentPlat.getEscandall();
                        for (int i = 0; i<escandall.size(); i++){
                            if (escandall.get(i).getId() > id) id = escandall.get(i).getId();
                        }
                        id++;
                        le.setId(id);
                        if (LiniaEscandall.insertaLiniaEscandall(em, le)){
                            refrescaLlistes(em);
                            em.refresh(currentPlat);
                            currentPlat = em.find(Plat.class, currentPlat.getId());
                            refreshPlat();
                            if (currentPlat == null){
                                edt.setVisible(false);
                            }
                        }
                    }
                }
            });

            buttonsPanel.add(btnAddEscandall);
            buttonsPanel.add(btnDelEscandall);

            eastFrame.add(scrollFrame);
            eastFrame.add(buttonsPanel);

            JPanel westFrame = new JPanel();
    //        westFrame.setLayout(new BoxLayout(westFrame, BoxLayout.Y_AXIS));

            JPanel platFrame = new JPanel();
            platFrame.setLayout(new BoxLayout(platFrame, BoxLayout.Y_AXIS));

            JPanel nomFrame = new JPanel();
            nomFrame.setLayout(new BoxLayout(nomFrame, BoxLayout.X_AXIS));
            JLabel lblNom = new JLabel("Plat: ");
            txtNom = new JLabel();

            nomFrame.add(lblNom);
            nomFrame.add(txtNom);

            JPanel descFrame = new JPanel();
            JLabel lblDesc = new JLabel("Descripció: ");
            txtDesc = new JLabel();

            descFrame.add(lblDesc);
            descFrame.add(txtDesc);

            JPanel preuFrame = new JPanel();
            preuFrame.setLayout(new BoxLayout(preuFrame, BoxLayout.X_AXIS));
            JLabel lblPreu = new JLabel("Preu: ");
            txtPreu = new JLabel();
            JLabel lblPreuE = new JLabel(" €");

            preuFrame.add(lblPreu);
            preuFrame.add(txtPreu);
            preuFrame.add(lblPreuE);

            JPanel disponibilitatFrame = new JPanel();
            disponibilitatFrame.setLayout(new BoxLayout(disponibilitatFrame, BoxLayout.X_AXIS));
            JLabel lblDisponibilitat = new JLabel("Disponible: ");
            txtDisponibilitat = new JLabel();

            disponibilitatFrame.add(lblDisponibilitat);
            disponibilitatFrame.add(txtDisponibilitat);

            JPanel categoriaFrame = new JPanel();
            categoriaFrame.setLayout(new BoxLayout(categoriaFrame, BoxLayout.X_AXIS));
            JLabel lblCategoria = new JLabel("Categoria: ");
            txtCategoria = new JLabel();

            categoriaFrame.add(lblCategoria);
            categoriaFrame.add(txtCategoria);

            platFrame.add(nomFrame);
            platFrame.add(descFrame);
            platFrame.add(preuFrame);
            platFrame.add(disponibilitatFrame);
            platFrame.add(categoriaFrame);

            westFrame.add(platFrame, BorderLayout.NORTH);

            southFrame = new JPanel();
            
            JLabel lblIngredients = new JLabel("Ingredient: ");
            JComboBox<Ingredient> cboIngredients = new JComboBox<Ingredient>();
            selectedIngredient = null;
            cboIngredients.addItem(null);
            for (int i = 0; i < ingredients.size(); i++) {
              cboIngredients.addItem(ingredients.get(i));
            }
            cboIngredients.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent action) {
                    JComboBox cb = (JComboBox)action.getSource();
                    selectedIngredient = (Ingredient)cb.getSelectedItem();
                }
            }); 
            
            JLabel lblUnitats = new JLabel("Quantitat: ");
            txtQuantitat = new JTextField("0");
            txtQuantitat.setColumns(5);
            txtQuantitat.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent d) {
                    changeSelectedQuantitat();
                }

                @Override
                public void removeUpdate(DocumentEvent arg0) {
                    changeSelectedQuantitat();
                }

                @Override
                public void changedUpdate(DocumentEvent arg0) {
                    changeSelectedQuantitat();
                }
            });
            
            JComboBox<Unitat> cboUnitats = new JComboBox<Unitat>();
            selectedUnitat = null;
            cboUnitats.addItem(null);
            for (int i = 0; i < unitats.size(); i++) {
              cboUnitats.addItem(unitats.get(i));
            }
            cboUnitats.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent action) {
                    JComboBox cb = (JComboBox)action.getSource();
                    selectedUnitat = (Unitat)cb.getSelectedItem();
                }
            });
                        
            Box bInsert = Box.createHorizontalBox();
            bInsert.add(lblIngredients);
            bInsert.add(Box.createHorizontalStrut(10));
            bInsert.add(cboIngredients);
            bInsert.add(Box.createHorizontalStrut(20));
            bInsert.add(lblUnitats);
            bInsert.add(Box.createHorizontalStrut(10));
            bInsert.add(txtQuantitat);
            bInsert.add(Box.createHorizontalStrut(30));
            bInsert.add(cboUnitats);
            southFrame.add(bInsert);

            this.add(eastFrame, BorderLayout.EAST);
            this.add(westFrame, BorderLayout.WEST);
            this.add(southFrame, BorderLayout.SOUTH);

            refreshPlat();
            
            this.pack();
            this.setVisible(true);
            Dimension mainFrameSize = new Dimension(700, 300);
            this.setSize(mainFrameSize);
            this.setResizable(false);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        }

        private void changeSelectedQuantitat(){
            try{
                selectedQuantitat = Integer.parseInt(txtQuantitat.getText());
            }
            catch(Exception e){
                selectedQuantitat = 0;
            }
        };
        
        // disponibilitat: 0 = Totes; 1 = Sí; 2 = No
        private void refreshListEscandall() {
            modelLlistaEscandall.clear();
            // Initialize the list with items
            List <LiniaEscandall> escandallat = currentPlat.getEscandall();
            for (int i = 0; i < escandallat.size(); i++) {
                modelLlistaEscandall.add(i, escandallat.get(i));
            }
            selectedEscandall = null;
        }

        private void refreshPlat(){
            txtNom.setText(currentPlat.getNom());
            txtDesc.setText(currentPlat.getDescripcio());
            txtPreu.setText(Double.toString(currentPlat.getPreu()));
            String s = "Sí";
            if (currentPlat.isDisponible() == false) s = "No";
            txtDisponibilitat.setText(s);
            txtCategoria.setText(currentPlat.getCategoria().toString());
            refreshListEscandall();
        }
    }
}
