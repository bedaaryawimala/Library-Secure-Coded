package PanelView;

import Controller.BukuController;
import Controller.KomikController;
import Controller.NovelController;
import Exception.InputKosongException;
import Exception.InputSpecialAtributeException;
import Model.Buku;
import Model.Komik;
import Model.Novel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.Year;
import java.util.List;

public class BukuMainPanel extends JPanel {

    private JTextField inputSearch;
    private JTextField inputIdBuku;
    private JTextField inputJudul;
    private JTextField inputTahunTerbit;
    private JTextField inputCover;

    private JComboBox<String> comboJenisBuku;

    private JButton btnCari;
    private JButton btnTambah;
    private JButton btnBarukan;
    private JButton btnHapus;
    private JButton btnBatalkan;
    private JButton btnSimpan;

    private JTable tableNovel;
    private JTable tableKomik;
    private DefaultTableModel modelNovel;
    private DefaultTableModel modelKomik;
    private BukuController bukuController;
    private NovelController novelController;
    private KomikController komikController;
    private String selectedId;

    public BukuMainPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        bukuController = new BukuController();
        novelController = new NovelController();
        komikController = new KomikController();
        initComponents();
        loadData("");
    }

    private void initComponents() {
        JLabel labelSearch = createLabel("Pencarian Buku", 20, 20);
        add(labelSearch);

        inputSearch = createTextField(20, 45, 520, 35);
        add(inputSearch);

        btnCari = createButton("Cari", new Color(25, 24, 83), 560, 45);
        add(btnCari);

        btnTambah = createButton("Tambah", new Color(34, 190, 84), 25, 110);
        btnBarukan = createButton("Barukan", new Color(255, 183, 0), 135, 110);
        btnHapus = createButton("Hapus", new Color(235, 20, 12), 245, 110);
        btnBatalkan = createButton("Batalkan", new Color(65, 71, 71), 355, 110);

        add(btnTambah);
        add(btnBarukan);
        add(btnHapus);
        add(btnBatalkan);

        JLabel labelId = createLabel("ID Buku", 25, 190);
        add(labelId);

        inputIdBuku = createTextField(25, 215, 270, 35);
        inputIdBuku.setEditable(false);
        add(inputIdBuku);

        JLabel labelTahun = createLabel("Tahun Terbit", 350, 190);
        add(labelTahun);

        inputTahunTerbit = createTextField(350, 215, 270, 35);
        add(inputTahunTerbit);

        JLabel labelCover = createLabel("Cover / Ilustrator", 720, 190);
        add(labelCover);

        inputCover = createTextField(720, 215, 270, 35);
        add(inputCover);

        JLabel labelJudul = createLabel("Judul", 25, 285);
        add(labelJudul);

        inputJudul = createTextField(25, 310, 270, 35);
        add(inputJudul);

        JLabel labelJenis = createLabel("Jenis Buku", 350, 285);
        add(labelJenis);

        comboJenisBuku = new JComboBox<>();
        comboJenisBuku.addItem("novel");
        comboJenisBuku.addItem("komik");
        comboJenisBuku.setBounds(350, 310, 130, 35);
        add(comboJenisBuku);

        btnSimpan = createButton("Simpan", new Color(34, 190, 84), 900, 370);
        add(btnSimpan);

        modelNovel = new DefaultTableModel();
        modelNovel.addColumn("ID Buku");
        modelNovel.addColumn("Judul Novel");
        modelNovel.addColumn("Tahun Terbit");
        modelNovel.addColumn("Cover");

        tableNovel = new JTable(modelNovel);
        JScrollPane scrollNovel = new JScrollPane(tableNovel);
        scrollNovel.setBounds(20, 420, 500, 220);
        add(scrollNovel);

        modelKomik = new DefaultTableModel();
        modelKomik.addColumn("ID Buku");
        modelKomik.addColumn("Judul Komik");
        modelKomik.addColumn("Tahun Terbit");
        modelKomik.addColumn("Ilustrator");

        tableKomik = new JTable(modelKomik);
        JScrollPane scrollKomik = new JScrollPane(tableKomik);
        scrollKomik.setBounds(540, 420, 500, 220);
        add(scrollKomik);

        btnTambah.addActionListener(e -> saveData());
        btnSimpan.addActionListener(e -> saveData());
        btnBarukan.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> deleteData());
        btnBatalkan.addActionListener(e -> clearForm());
        btnCari.addActionListener(e -> loadData(inputSearch.getText()));
        tableNovel.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                setSelectedDataFromTable(tableNovel, modelNovel, "novel");
            }
        });
        tableKomik.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                setSelectedDataFromTable(tableKomik, modelKomik, "komik");
            }
        });
        updateButtonState();
    }

    private void loadData(String keyword) {
        selectedId = null;
        modelNovel.setRowCount(0);
        modelKomik.setRowCount(0);
        addBukuRows(bukuController.showDataBuku("novel"), modelNovel, keyword);
        addBukuRows(bukuController.showDataBuku("komik"), modelKomik, keyword);
        updateButtonState();
    }

    private void addBukuRows(List<Buku> list, DefaultTableModel model, String keyword) {
        for (Buku b : list) {
            if (keyword == null || keyword.trim().isEmpty()
                    || b.getJudul().toLowerCase().contains(keyword.trim().toLowerCase())
                    || b.getId_buku().toLowerCase().contains(keyword.trim().toLowerCase())) {
                model.addRow(new Object[]{
                        b.getId_buku(),
                        b.getJudul(),
                        b.getTahun_terbit(),
                        b.getSpecial()
                });
            }
        }
    }

    private void saveData() {
        try {
            String jenis = comboJenisBuku.getSelectedItem().toString();
            validateBukuInput(jenis);
            String id = bukuController.generateId();
            int tahunTerbit = Integer.parseInt(inputTahunTerbit.getText().trim());
            if (jenis.equals("novel")) {
                Novel novel = new Novel(inputCover.getText().trim(), id, inputJudul.getText().trim(), jenis, tahunTerbit);
                novelController.insertDataBuku(novel);
            } else {
                Komik komik = new Komik(inputCover.getText().trim(), id, inputJudul.getText().trim(), jenis, tahunTerbit);
                komikController.insertDataBuku(komik);
            }

            clearForm();
            loadData("");
        } catch (InputKosongException e) {
            JOptionPane.showMessageDialog(this, e.message());
        } catch (InputSpecialAtributeException e) {
            JOptionPane.showMessageDialog(this, e.message(comboJenisBuku.getSelectedItem().toString()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun terbit harus berupa angka.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void updateData() {
        if (selectedId == null) {
            setSelectedData();
        }

        if (selectedId == null) {
            JOptionPane.showMessageDialog(this, "Pilih data buku yang mau diubah.");
            return;
        }

        try {
            String jenis = comboJenisBuku.getSelectedItem().toString();
            validateBukuInput(jenis);
            int tahunTerbit = Integer.parseInt(inputTahunTerbit.getText().trim());

            if (jenis.equals("novel")) {
                Novel novel = new Novel(inputCover.getText().trim(), selectedId, inputJudul.getText().trim(), jenis, tahunTerbit);
                novelController.updateDataBuku(novel);
            } else {
                Komik komik = new Komik(inputCover.getText().trim(), selectedId, inputJudul.getText().trim(), jenis, tahunTerbit);
                komikController.updateDataBuku(komik);
            }

            clearForm();
            loadData("");
        } catch (InputKosongException e) {
            JOptionPane.showMessageDialog(this, e.message());
        } catch (InputSpecialAtributeException e) {
            JOptionPane.showMessageDialog(this, e.message(comboJenisBuku.getSelectedItem().toString()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Tahun terbit harus berupa angka.");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void validateBukuInput(String jenis) throws InputKosongException, InputSpecialAtributeException {
        String judul = inputJudul.getText().trim();
        String tahun = inputTahunTerbit.getText().trim();
        String special = inputCover.getText().trim();

        if (judul.isEmpty() || tahun.isEmpty()) {
            throw new InputKosongException();
        }

        if (special.isEmpty()) {
            throw new InputSpecialAtributeException();
        }

        int tahunTerbit = Integer.parseInt(tahun);
        int tahunSekarang = Year.now().getValue();
        if (tahunTerbit < 1000 || tahunTerbit > tahunSekarang) {
            throw new IllegalArgumentException("Tahun terbit harus di antara 1000 sampai " + tahunSekarang + ".");
        }
    }

    private void setSelectedData() {
        if (tableNovel.getSelectedRow() >= 0) {
            setSelectedDataFromTable(tableNovel, modelNovel, "novel");
        } else if (tableKomik.getSelectedRow() >= 0) {
            setSelectedDataFromTable(tableKomik, modelKomik, "komik");
        }
    }

    private void setSelectedDataFromTable(JTable table, DefaultTableModel model, String jenis) {
        int selectedRow = table.getSelectedRow();
        if (selectedRow < 0) {
            return;
        }

        selectedId = model.getValueAt(selectedRow, 0).toString();
        inputIdBuku.setText(selectedId);
        inputJudul.setText(model.getValueAt(selectedRow, 1).toString());
        inputTahunTerbit.setText(model.getValueAt(selectedRow, 2).toString());
        inputCover.setText(model.getValueAt(selectedRow, 3).toString());
        comboJenisBuku.setSelectedItem(jenis);
        updateButtonState();

        if (jenis.equals("novel")) {
            tableKomik.clearSelection();
        } else {
            tableNovel.clearSelection();
        }
    }

    private void deleteData() {
        if (selectedId == null) {
            setSelectedData();
        }

        if (selectedId != null) {
            bukuController.deleteDataBuku(selectedId);
            clearForm();
            loadData("");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data buku yang mau dihapus.");
        }
    }

    private void clearForm() {
        selectedId = null;
        inputIdBuku.setText("");
        inputJudul.setText("");
        inputTahunTerbit.setText("");
        inputCover.setText("");
        tableNovel.clearSelection();
        tableKomik.clearSelection();
        updateButtonState();
    }

    private void updateButtonState() {
        boolean hasSelection = selectedId != null;
        btnBarukan.setEnabled(hasSelection);
        btnHapus.setEnabled(hasSelection);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 200, 20);
        label.setFont(new Font("Arial", Font.BOLD, 13));
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        textField.setBackground(new Color(65, 71, 71));
        textField.setForeground(Color.WHITE);
        return textField;
    }

    private JButton createButton(String text, Color color, int x, int y) {
        JButton button = new JButton(text);
        button.setBounds(x, y, 95, 35);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 13));
        button.setFocusPainted(false);
        return button;
    }
}
