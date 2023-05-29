import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class JView extends JFrame {

    JButton jButtonImport = new JButton("Import data:");
    JButton jButtonExport = new JButton("Export calculations:");
    JButton jButtonClose = new JButton("Close program");
    JPanel jPanelImport = new JPanel();
    JLabel jLabelImportInfo = new JLabel();
    JLabel jLabelExportInfo = new JLabel();
    JPanel jPanelExport = new JPanel();
    JPanel jPanelClose = new JPanel();
    JFileChooser chooser = new JFileChooser();

    public JView() {
        initComponents();
    }

    private void initComponents() {

        this.setBounds(100, 100, 300, 180);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        jButtonImport.addActionListener(this::importExcelActionPerformed);
        jButtonExport.addActionListener(this::exportExcelActionPerformed);
        jButtonExport.setEnabled(false);
        jButtonClose.addActionListener(this::closeActionPerformed);
        jPanelImport.add(jButtonImport);
        jLabelImportInfo.setBounds(100, 100, 500, 100);
        jLabelImportInfo.setText("Import excel");
        jPanelImport.add(jLabelImportInfo);
        jPanelExport.add(jButtonExport);
        jLabelExportInfo.setBounds(100, 100, 500, 100);
        jLabelExportInfo.setText("Export excel");
        jPanelExport.add(jLabelExportInfo);
        jPanelClose.setBounds(100, 100, 300, 300);
        jPanelClose.add(jButtonClose);
        container.setLayout(new FlowLayout(FlowLayout.CENTER));
        container.add(jPanelImport);
        container.add(jPanelExport);
        container.add(jPanelClose);
    }

    public void importExcelActionPerformed(ActionEvent event) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("", "xlsx");
        chooser.setFileFilter(filter);
        chooser.setCurrentDirectory(new File("./src/main/resources"));
        chooser.showDialog(null, "Choose file:");
        File file = chooser.getSelectedFile();
        try {
            ExcelReader.readFromExcel(file);
            jLabelImportInfo.setText("Excel imported");
            jButtonExport.setEnabled(true);
        } catch (IOException | InvalidFormatException | IllegalStateException e) {
            jLabelImportInfo.setText("Import error");
        }
    }

    public void exportExcelActionPerformed(ActionEvent event) {
        try {
            ExcelReader.writeToExcel();
            jLabelExportInfo.setText("Excel exported");
        } catch (IOException e) {
            jLabelExportInfo.setText("Export error");
        }
    }

    public void closeActionPerformed(ActionEvent event) {
        this.dispose();
    }
}