package PanelView;

import Controller.BukuControllerE;
import Controller.PeminjamControllerE;
import Controller.PeminjamanController;
import Exception.InputKosongException;
import Model.BukuE;
import Model.PeminjamE;
import Model.Peminjaman;
import Table.TablePeminjaman;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class PeminjamanMainPanel extends JPanel {

    private JTextField inputSearch;
    private JComboBox<PeminjamE> comboPeminjam;
    private JComboBox<BukuE> comboBuku;

    private JCheckBox cbThriller;
    private JCheckBox cbMystery;
    private JCheckBox cbSliceOfLife;
    private JCheckBox cbRomance;

    private JRadioButton rbLantai1;
    private JRadioButton rbLantai2;
    private JRadioButton rbLantai3;
    private ButtonGroup groupWilayah;

    private JTextField inputTanggalMeminjam;
    private JTextField inputTanggalMengembalikan;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JButton btnCari;
    private JButton btnTambah;
    private JButton btnBarukan;
    private JButton btnHapus;
    private JButton btnSimpan;
    private JButton btnBatalkan;

    private JTable TablePeminjaman;
    private PeminjamanController peminjamanController;
    private PeminjamControllerE peminjamControllerE;
    private BukuControllerE bukuControllerE;
    private List<Peminjaman> listPeminjaman;
    private Integer selectedId;

    public PeminjamanMainPanel() {
        setLayout(null);
        setBackground(Color.WHITE);
        peminjamanController = new PeminjamanController();
        peminjamControllerE = new PeminjamControllerE();
        bukuControllerE = new BukuControllerE();
        listPeminjaman = new ArrayList<>();
        initComponents();
        loadDropdown();
        loadData("");
    }

    private void initComponents() {
        JLabel labelSearch = createLabel("Pencarian Peminjaman", 20, 20);
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

        JLabel labelPeminjam = createLabel("Nama Peminjam", 25, 190);
        add(labelPeminjam);

        comboPeminjam = new JComboBox<>();
        comboPeminjam.setBounds(25, 220, 300, 30);
        add(comboPeminjam);

        JLabel labelBuku = createLabel("Judul Buku", 25, 280);
        add(labelBuku);

        comboBuku = new JComboBox<>();
        comboBuku.setBounds(25, 310, 300, 30);
        add(comboBuku);

        JLabel labelGenre = createLabel("Genre", 380, 190);
        add(labelGenre);

        cbThriller = new JCheckBox("Thriller");
        cbMystery = new JCheckBox("Mystery");
        cbSliceOfLife = new JCheckBox("Slice of Life");
        cbRomance = new JCheckBox("Romance");

        cbThriller.setBounds(380, 220, 150, 25);
        cbMystery.setBounds(380, 250, 150, 25);
        cbSliceOfLife.setBounds(380, 280, 150, 25);
        cbRomance.setBounds(380, 310, 150, 25);

        add(cbThriller);
        add(cbMystery);
        add(cbSliceOfLife);
        add(cbRomance);

        JLabel labelWilayah = createLabel("Wilayah Buku", 560, 190);
        add(labelWilayah);

        rbLantai1 = new JRadioButton("Lantai 1");
        rbLantai2 = new JRadioButton("Lantai 2");
        rbLantai3 = new JRadioButton("Lantai 3");

        rbLantai1.setBounds(560, 220, 120, 25);
        rbLantai2.setBounds(560, 250, 120, 25);
        rbLantai3.setBounds(560, 280, 120, 25);

        groupWilayah = new ButtonGroup();
        groupWilayah.add(rbLantai1);
        groupWilayah.add(rbLantai2);
        groupWilayah.add(rbLantai3);

        add(rbLantai1);
        add(rbLantai2);
        add(rbLantai3);

        JLabel labelTanggalMeminjam = createLabel("Tanggal Meminjam", 740, 190);
        add(labelTanggalMeminjam);

        inputTanggalMeminjam = createTextField(740, 220, 255, 30);
        add(inputTanggalMeminjam);
        add(createDateButton(inputTanggalMeminjam, 1005, 220));

        JLabel labelTanggalMengembalikan = createLabel("Tanggal Mengembalikan", 740, 280);
        add(labelTanggalMengembalikan);

        inputTanggalMengembalikan = createTextField(740, 310, 255, 30);
        add(inputTanggalMengembalikan);
        add(createDateButton(inputTanggalMengembalikan, 1005, 310));

        btnSimpan = createButton("Simpan", new Color(34, 190, 84), 820, 390);

        add(btnSimpan);

        TablePeminjaman = new JTable();
        TablePeminjaman.setModel(new TablePeminjaman(new ArrayList<>()));

        JScrollPane scrollPane = new JScrollPane(TablePeminjaman);
        scrollPane.setBounds(20, 450, 1020, 200);
        add(scrollPane);

        btnTambah.addActionListener(e -> saveData());
        btnBatalkan.addActionListener(e -> clearForm());
        btnSimpan.addActionListener(e -> saveData());
        btnBarukan.addActionListener(e -> updateData());
        btnHapus.addActionListener(e -> deleteData());
        btnCari.addActionListener(e -> loadData(inputSearch.getText()));
        TablePeminjaman.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                setSelectedData();
            }
        });
        updateButtonState();
    }

    private void loadDropdown() {
        comboPeminjam.removeAllItems();
        for (PeminjamE p : peminjamControllerE.showListPeminjam()) {
            comboPeminjam.addItem(p);
        }

        comboBuku.removeAllItems();
        for (BukuE b : bukuControllerE.showListBuku()) {
            comboBuku.addItem(b);
        }
    }

    private void loadData(String keyword) {
        selectedId = null;
        listPeminjaman = peminjamanController.showDataPeminjaman(keyword == null ? "" : keyword.trim());
        TablePeminjaman.setModel(new TablePeminjaman(listPeminjaman));
        updateButtonState();
    }

    private void saveData() {
        try {
            peminjamanController.insertDataPeminjaman(getPeminjamanFromInput());
            clearForm();
            loadData("");
        } catch (InputKosongException e) {
            JOptionPane.showMessageDialog(this, e.message());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private Peminjaman getPeminjamanFromInput() throws InputKosongException {
        PeminjamE peminjam = (PeminjamE) comboPeminjam.getSelectedItem();
        BukuE buku = (BukuE) comboBuku.getSelectedItem();
        String genre = getSelectedGenre();
        String wilayah = getSelectedWilayah();
        String tanggalMeminjam = inputTanggalMeminjam.getText().trim();
        String tanggalMengembalikan = inputTanggalMengembalikan.getText().trim();

        if (peminjam == null || buku == null) {
            throw new IllegalArgumentException("Peminjam dan buku harus dipilih.");
        }

        if (genre.isEmpty() || wilayah.isEmpty() || tanggalMeminjam.isEmpty() || tanggalMengembalikan.isEmpty()) {
            throw new InputKosongException();
        }

        LocalDate tanggalPinjam;
        LocalDate tanggalKembali;
        try {
            tanggalPinjam = LocalDate.parse(tanggalMeminjam, dateFormatter);
            tanggalKembali = LocalDate.parse(tanggalMengembalikan, dateFormatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Format tanggal harus yyyy-MM-dd.");
        }

        if (tanggalKembali.isBefore(tanggalPinjam)) {
            throw new IllegalArgumentException("Tanggal mengembalikan tidak boleh sebelum tanggal meminjam.");
        }

        return new Peminjaman(
                peminjam.getId_peminjam(),
                buku.getId_buku(),
                genre,
                wilayah,
                tanggalMeminjam,
                tanggalMengembalikan,
                peminjam,
                buku
        );
    }

    private void updateData() {
        if (selectedId == null) {
            setSelectedData();
        }

        if (selectedId == null) {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman yang mau diubah.");
            return;
        }

        try {
            peminjamanController.updatePeminjaman(getPeminjamanFromInput(), selectedId);
            clearForm();
            loadData("");
        } catch (InputKosongException e) {
            JOptionPane.showMessageDialog(this, e.message());
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void setSelectedData() {
        int selectedRow = TablePeminjaman.getSelectedRow();
        if (selectedRow < 0 || selectedRow >= listPeminjaman.size()) {
            return;
        }

        Peminjaman peminjaman = listPeminjaman.get(selectedRow);
        selectedId = peminjaman.getId_jadwal_bertugas();
        selectPeminjam(peminjaman.getId_Peminjam());
        selectBuku(peminjaman.getId_Buku());
        setSelectedGenre(peminjaman.getGenre());
        setSelectedWilayah(peminjaman.getWilayah());
        inputTanggalMeminjam.setText(peminjaman.getTanggal_meminjam());
        inputTanggalMengembalikan.setText(peminjaman.getTanggal_mengembalikan());
        updateButtonState();
    }

    private void selectPeminjam(int idPeminjam) {
        for (int i = 0; i < comboPeminjam.getItemCount(); i++) {
            if (comboPeminjam.getItemAt(i).getId_peminjam() == idPeminjam) {
                comboPeminjam.setSelectedIndex(i);
                return;
            }
        }
    }

    private void selectBuku(String idBuku) {
        for (int i = 0; i < comboBuku.getItemCount(); i++) {
            if (comboBuku.getItemAt(i).getId_buku().equals(idBuku)) {
                comboBuku.setSelectedIndex(i);
                return;
            }
        }
    }

    private void deleteData() {
        if (selectedId == null) {
            setSelectedData();
        }

        if (selectedId != null) {
            peminjamanController.deletePeminjaman(selectedId);
            clearForm();
            loadData("");
        } else {
            JOptionPane.showMessageDialog(this, "Pilih data peminjaman yang mau dihapus.");
        }
    }

    private void clearForm() {
        selectedId = null;
        if (comboPeminjam.getItemCount() > 0) {
            comboPeminjam.setSelectedIndex(0);
        }
        if (comboBuku.getItemCount() > 0) {
            comboBuku.setSelectedIndex(0);
        }
        cbThriller.setSelected(false);
        cbMystery.setSelected(false);
        cbSliceOfLife.setSelected(false);
        cbRomance.setSelected(false);
        groupWilayah.clearSelection();
        inputTanggalMeminjam.setText("");
        inputTanggalMengembalikan.setText("");
        TablePeminjaman.clearSelection();
        updateButtonState();
    }

    private void updateButtonState() {
        boolean hasSelection = selectedId != null;
        btnBarukan.setEnabled(hasSelection);
        btnHapus.setEnabled(hasSelection);
    }

    private JLabel createLabel(String text, int x, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(x, y, 220, 20);
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

    private JButton createDateButton(JTextField target, int x, int y) {
        JButton button = new JButton("...");
        button.setBounds(x, y, 35, 30);
        button.setBackground(new Color(25, 24, 83));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setFocusPainted(false);
        button.addActionListener(e -> showCalendar(target));
        return button;
    }

    private void showCalendar(JTextField target) {
        JDialog dialog = new JDialog(SwingUtilities.getWindowAncestor(this), "Pilih Tanggal", Dialog.ModalityType.APPLICATION_MODAL);
        dialog.setSize(330, 310);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(8, 8));

        LocalDate initialDate = getDateFromText(target);
        YearMonth[] currentMonth = {YearMonth.from(initialDate)};

        JPanel headerPanel = new JPanel(new BorderLayout());
        JButton btnPrev = new JButton("<");
        JButton btnNext = new JButton(">");
        JLabel labelMonth = new JLabel("", SwingConstants.CENTER);
        labelMonth.setFont(new Font("Arial", Font.BOLD, 14));
        headerPanel.add(btnPrev, BorderLayout.WEST);
        headerPanel.add(labelMonth, BorderLayout.CENTER);
        headerPanel.add(btnNext, BorderLayout.EAST);

        JPanel calendarPanel = new JPanel(new GridLayout(0, 7, 4, 4));

        Runnable refreshCalendar = () -> {
            calendarPanel.removeAll();
            labelMonth.setText(currentMonth[0].getMonth() + " " + currentMonth[0].getYear());

            String[] dayNames = {"Min", "Sen", "Sel", "Rab", "Kam", "Jum", "Sab"};
            for (String dayName : dayNames) {
                JLabel labelDay = new JLabel(dayName, SwingConstants.CENTER);
                labelDay.setFont(new Font("Arial", Font.BOLD, 12));
                calendarPanel.add(labelDay);
            }

            LocalDate firstDay = currentMonth[0].atDay(1);
            int blankDays = firstDay.getDayOfWeek().getValue() % 7;
            for (int i = 0; i < blankDays; i++) {
                calendarPanel.add(new JLabel(""));
            }

            for (int day = 1; day <= currentMonth[0].lengthOfMonth(); day++) {
                LocalDate selectedDate = currentMonth[0].atDay(day);
                JButton btnDay = new JButton(String.valueOf(day));
                btnDay.addActionListener(e -> {
                    target.setText(selectedDate.format(dateFormatter));
                    dialog.dispose();
                });
                calendarPanel.add(btnDay);
            }

            calendarPanel.revalidate();
            calendarPanel.repaint();
        };

        btnPrev.addActionListener(e -> {
            currentMonth[0] = currentMonth[0].minusMonths(1);
            refreshCalendar.run();
        });
        btnNext.addActionListener(e -> {
            currentMonth[0] = currentMonth[0].plusMonths(1);
            refreshCalendar.run();
        });

        dialog.add(headerPanel, BorderLayout.NORTH);
        dialog.add(calendarPanel, BorderLayout.CENTER);
        refreshCalendar.run();
        dialog.setVisible(true);
    }

    private LocalDate getDateFromText(JTextField target) {
        try {
            return LocalDate.parse(target.getText(), dateFormatter);
        } catch (DateTimeParseException e) {
            return LocalDate.now();
        }
    }

    public String getSelectedGenre() {
        boolean thriller = cbThriller.isSelected();
        boolean mystery = cbMystery.isSelected();
        boolean slice = cbSliceOfLife.isSelected();
        boolean romance = cbRomance.isSelected();

        if (thriller && mystery) {
            return "Thriller + Mystery";
        } else if (slice && romance) {
            return "Slice of Life + Romance";
        } else if (thriller) {
            return "Thriller";
        } else if (mystery) {
            return "Mystery";
        } else if (slice) {
            return "Slice of Life";
        } else if (romance) {
            return "Romance";
        } else {
            return "";
        }
    }

    public String getSelectedWilayah() {
        if (rbLantai1.isSelected()) {
            return "Lantai 1";
        } else if (rbLantai2.isSelected()) {
            return "Lantai 2";
        } else if (rbLantai3.isSelected()) {
            return "Lantai 3";
        } else {
            return "";
        }
    }

    private void setSelectedGenre(String genre) {
        cbThriller.setSelected(genre != null && genre.contains("Thriller"));
        cbMystery.setSelected(genre != null && genre.contains("Mystery"));
        cbSliceOfLife.setSelected(genre != null && genre.contains("Slice of Life"));
        cbRomance.setSelected(genre != null && genre.contains("Romance"));
    }

    private void setSelectedWilayah(String wilayah) {
        if ("Lantai 1".equals(wilayah)) {
            rbLantai1.setSelected(true);
        } else if ("Lantai 2".equals(wilayah)) {
            rbLantai2.setSelected(true);
        } else if ("Lantai 3".equals(wilayah)) {
            rbLantai3.setSelected(true);
        } else {
            groupWilayah.clearSelection();
        }
    }
}
