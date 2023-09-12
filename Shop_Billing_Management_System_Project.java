import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import java.sql.*;
import net.proteanit.sql.DbUtils;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;


public class Shop_Billing_Management_System_Main {

private JFrame frame;
private JTextField srno;
private JTextField newitem;
private JTextField newprice;
private JTextField newquantity;
private PreparedStatement ps;
private PreparedStatement opset;
private JButton additem;
public int cust_no;
private JTable table;
    public double total_ammount = 0;
    private JTextField final_totaltext;
    private PreparedStatement cleardata;
    private PreparedStatement user_ids;
    private JTextField searchbox;
    private JTable table_1;
    private JComboBox comboBox;
    public int cust_ids = 1;
    private JTextField custnum;
    private PreparedStatement firstopn;
    public int searchitemname;
    public int searchcustid;
    private PreparedStatement searchqry1;
    private Connection conn;
    private JTextField totalsales;
    private PreparedStatement totalsalesqr;
   
   
   
/**
* Launch the application.
*/
public static void main(String[] args) {
EventQueue.invokeLater(new Runnable() {
public void run() {
try {
Shop_Billing_Management_System_Main window = new Shop_Billing_Management_System_Main();
window.frame.setVisible(true);
} catch (Exception e) {
e.printStackTrace();
}
}
});
}

/**
* Create the application.
*/
public Shop_Billing_Management_System_Main() {
try {
String opnquery = "Select * from newbilling_table where cust_id = "+cust_ids+";";
String Firstopn = "Select * from newbilling_table where cust_id = "+(cust_ids+1)+";";
Class.forName("com.mysql.jdbc.Driver");
conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/billing_data?characterEncoding=utf8","root","admin123");
ps = conn.prepareStatement("insert into newbilling_table(srno,item_id,item_name,quantity,price,category,cust_id) values(?,?,?,?,?,?,?)");
opset = conn.prepareStatement(opnquery);
cleardata = conn.prepareStatement("delete From newbilling_table");
user_ids = conn.prepareStatement("select srno from newbilling_table where srno=(SELECT LAST_INSERT_ID())");
   totalsalesqr = conn.prepareStatement("select SUM(price) from newbilling_table");
firstopn = conn.prepareStatement(Firstopn);
ResultSet cs = user_ids.executeQuery();
cust_no = cs.getInt(1);
System.out.println(cust_no);
}
catch(Exception e) {
System.out.println(e);
}
initialize();
try {
cleardata.executeUpdate();
} catch (SQLException e) {
// TODO Auto-generated catch block
e.printStackTrace();
}
custnum.setText(String.valueOf(cust_ids));

JLabel lblNewLabel_7 = new JLabel("Customer No.");
lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 12));
lblNewLabel_7.setBounds(695, 79, 107, 14);
frame.getContentPane().add(lblNewLabel_7);

JLabel lblNewLabel_9 = new JLabel("Total Sales :");
lblNewLabel_9.setFont(new Font("Tahoma", Font.BOLD, 12));
lblNewLabel_9.setBounds(889, 506, 91, 14);
frame.getContentPane().add(lblNewLabel_9);

totalsales = new JTextField();
totalsales.setBounds(971, 503, 86, 20);
frame.getContentPane().add(totalsales);
totalsales.setColumns(10);


}

/**
* Initialize the contents of the frame.
*/
private void initialize() {
frame = new JFrame();
frame.setBounds(100, 100, 1110, 571);
frame.setTitle("SHOP BILLING MANAGEMENT SYSTEM");
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.getContentPane().setLayout(null);

JSeparator separator = new JSeparator();
separator.setBounds(0, 27, 1094, 2);
frame.getContentPane().add(separator);

JLabel lblNewLabel = new JLabel("SHOP BILLING MANAGEMENT SYSTEM");
lblNewLabel.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
lblNewLabel.setBounds(270, 41, 363, 14);
frame.getContentPane().add(lblNewLabel);

JSeparator separator_1 = new JSeparator();
separator_1.setBounds(0, 66, 1094, 2);
frame.getContentPane().add(separator_1);

JLabel lblNewLabel_1 = new JLabel("Sr. No.");
lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 14));
lblNewLabel_1.setBounds(10, 79, 60, 14);
frame.getContentPane().add(lblNewLabel_1);

JLabel lblNewLabel_2 = new JLabel("Item Name");
lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 14));
lblNewLabel_2.setBounds(80, 79, 91, 14);
frame.getContentPane().add(lblNewLabel_2);

srno = new JTextField();
srno.setBounds(10, 104, 46, 20);
frame.getContentPane().add(srno);
srno.setColumns(10);

newitem = new JTextField();
newitem.setBounds(80, 104, 180, 20);
frame.getContentPane().add(newitem);
newitem.setColumns(10);

JLabel lblNewLabel_3 = new JLabel("Price.");
lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 14));
lblNewLabel_3.setBounds(270, 79, 52, 14);
frame.getContentPane().add(lblNewLabel_3);

newprice = new JTextField();
newprice.setBounds(270, 104, 86, 20);
frame.getContentPane().add(newprice);
newprice.setColumns(10);

JLabel lblNewLabel_4 = new JLabel("Oty.");
lblNewLabel_4.setFont(new Font("Tahoma", Font.BOLD, 14));
lblNewLabel_4.setBounds(366, 73, 46, 26);
frame.getContentPane().add(lblNewLabel_4);

newquantity = new JTextField();
newquantity.setBounds(366, 104, 46, 20);
frame.getContentPane().add(newquantity);
newquantity.setColumns(10);

additem = new JButton("ADD");
additem.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
String itemname,srnom,coboboxselected;
int qtty,prices;
double gstrate = 0.05;
ResultSet totalsalesdt = null;
int s = 1;

itemname = newitem.getText();
srnom = srno.getText();
qtty = Integer.parseInt(newquantity.getText());
prices = Integer.parseInt(newprice.getText());
coboboxselected = comboBox.getSelectedItem().toString();

System.out.println(qtty);
cust_no+=1;
switch(coboboxselected)
{
case "Frozen Vagitables.":
gstrate = 0.05;
break;
case "Spices.":
gstrate = 0.05;
break;
case "Plastic Waste.":
gstrate = 0.05;
break;
case "Milk Product.":
gstrate = 0.12;
break;
case "Fruits.":
gstrate = 0.12;
break;
case "Purses and Handbags.":
gstrate = 0.12;
break;
case "Electronics.":
gstrate = 0.18;
break;
case "Skin Care Product":
gstrate = 0.18;
break;
}

String finsrnom,finitemname;
int finqtty;
double finprice, gst;

finsrnom = String.valueOf(srnom);
finitemname = itemname;
finqtty = qtty;
finprice = prices * qtty;
gst = gstrate * finprice;
finprice = finprice+gst;
total_ammount += finprice;
srno.setText("");
newitem.setText("");
newquantity.setText("");
newprice.setText("");

try {
ps.setInt(1, cust_no);
ps.setString(2, finsrnom);
ps.setString(3, finitemname);
ps.setInt(4, finqtty);
ps.setDouble(5, finprice);
ps.setString(6, coboboxselected);
ps.setInt(7, cust_ids);
ps.executeUpdate();
}

catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
if(cust_ids == s+1)
{
try {
ResultSet rs1 = firstopn.executeQuery();
table.setModel(DbUtils.resultSetToTableModel(rs1));

}catch(SQLException e3){
System.out.println(e3);
}
s+=1;
}
else {
try {
ResultSet rs = opset.executeQuery();
table.setModel(DbUtils.resultSetToTableModel(rs));

}catch(SQLException e2){
System.out.println(e2);
}
}
System.out.println(finsrnom);
System.out.println(finitemname);
System.out.println(finqtty);
System.out.println(finprice);
System.out.println(gst);
System.out.println(gstrate);
final_totaltext.setText(String.valueOf("₹ "+total_ammount+"/-"));
try {
totalsalesdt = totalsalesqr.executeQuery();
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
try {
if(totalsalesdt.next())
{
String sum = null;
try {
sum = totalsalesdt.getString("SUM(price)");
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
totalsales.setText(sum);
}
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
}

   
});
additem.setBounds(600, 103, 74, 23);
frame.getContentPane().add(additem);

JSeparator separator_2 = new JSeparator();
separator_2.setBounds(604, 114, 1, 406);
frame.getContentPane().add(separator_2);

JSeparator separator_3 = new JSeparator();
separator_3.setBounds(615, 118, -10, 381);
frame.getContentPane().add(separator_3);

JSeparator separator_4 = new JSeparator();
separator_4.setBounds(604, 114, 1, 396);
frame.getContentPane().add(separator_4);

JSeparator separator_5 = new JSeparator();
separator_5.setOrientation(SwingConstants.VERTICAL);
separator_5.setBounds(604, 503, 1, -387);
frame.getContentPane().add(separator_5);

JScrollPane scrollPane = new JScrollPane();
scrollPane.setBounds(10, 135, 664, 341);
frame.getContentPane().add(scrollPane);

table = new JTable();
scrollPane.setViewportView(table);
table.setModel(new DefaultTableModel(
new Object[][] {
},
new String[] {
"Sr.No.", "Item_id", "Item_Name", "Qty.", "Total_Price", "Category"
}
) {
Class[] columnTypes = new Class[] {
Object.class, Object.class, Object.class, Object.class, Object.class, String.class
};
public Class getColumnClass(int columnIndex) {
return columnTypes[columnIndex];
}
});
table.getColumnModel().getColumn(0).setPreferredWidth(43);
table.getColumnModel().getColumn(1).setPreferredWidth(50);
table.getColumnModel().getColumn(2).setPreferredWidth(145);
table.getColumnModel().getColumn(3).setPreferredWidth(45);
table.getColumnModel().getColumn(4).setPreferredWidth(79);
table.getColumnModel().getColumn(5).setPreferredWidth(107);

JLabel lblNewLabel_5 = new JLabel(" Total+GST :");
lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 12));
lblNewLabel_5.setBounds(434, 487, 94, 14);
frame.getContentPane().add(lblNewLabel_5);

final_totaltext = new JTextField();
final_totaltext.setBounds(526, 485, 148, 20);
frame.getContentPane().add(final_totaltext);
final_totaltext.setColumns(10);

JSeparator separator_6 = new JSeparator();
separator_6.setOrientation(SwingConstants.VERTICAL);
separator_6.setBounds(684, 66, 1, 466);
frame.getContentPane().add(separator_6);

JLabel lblNewLabel_6 = new JLabel("Category");
lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 12));
lblNewLabel_6.setBounds(425, 81, 74, 14);
frame.getContentPane().add(lblNewLabel_6);

   comboBox = new JComboBox();
comboBox.setModel(new DefaultComboBoxModel(new String[] {"Frozen Vagitables.", "Spices.", "Plastic Waste.", "Milk Product.", "Fruits.", "Purses and Handbags.", "Electronics.", "Skin Care Product."}));
comboBox.setBounds(422, 103, 168, 22);
frame.getContentPane().add(comboBox);

searchbox = new JTextField();
searchbox.setBounds(693, 104, 148, 20);
frame.getContentPane().add(searchbox);
searchbox.setColumns(10);

JButton searchbtn = new JButton("Search");
searchbtn.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
searchitemname = Integer.parseInt(searchbox.getText());
try {
searchqry1 = conn.prepareStatement("Select srno, item_name, quantity, price, category,cust_id from newbilling_table where cust_id = "+searchitemname+";");
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
 

try {
ResultSet rs3 = searchqry1.executeQuery();
table_1.setModel(DbUtils.resultSetToTableModel(rs3));
System.out.println(rs3);

}catch(SQLException e2){
System.out.println(e2);
}
}
});
searchbtn.setBounds(877, 103, 86, 23);
frame.getContentPane().add(searchbtn);

JButton alldisbtn = new JButton("Show All");
alldisbtn.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
try {
searchqry1 = conn.prepareStatement("select srno, item_name, quantity, price, category from newbilling_table; ");
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}
try {
ResultSet rs4 = searchqry1.executeQuery();
table_1.setModel(DbUtils.resultSetToTableModel(rs4));
} catch (SQLException e1) {
// TODO Auto-generated catch block
e1.printStackTrace();
}

}
});
alldisbtn.setBounds(973, 103, 100, 23);
frame.getContentPane().add(alldisbtn);

JScrollPane scrollPane_1 = new JScrollPane();
scrollPane_1.setBounds(695, 135, 378, 367);
frame.getContentPane().add(scrollPane_1);

table_1 = new JTable();
scrollPane_1.setViewportView(table_1);
table_1.setModel(new DefaultTableModel(
new Object[][] {
},
new String[] {
"Sr.No.", "Item Name", "Quantity", "Price", "Category", "Cust_Id"
}
) {
Class[] columnTypes = new Class[] {
Integer.class, String.class, Integer.class, Long.class, String.class, Integer.class
};
public Class getColumnClass(int columnIndex) {
return columnTypes[columnIndex];
}
});

JButton btnNewButton = new JButton("New Customer");
btnNewButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
table.setModel(new DefaultTableModel(null,new String[] {"Sr.No.", "Item_id", "Item_Name", "Qty.", "Total_Price", "Category"} ));
final_totaltext.setText("");
total_ammount = 0;
cust_ids += 1;
custnum.setText(String.valueOf(cust_ids));
}
});
btnNewButton.setBounds(10, 487, 122, 23);
frame.getContentPane().add(btnNewButton);

custnum = new JTextField();
custnum.setBounds(10, 44, 74, 14);
frame.getContentPane().add(custnum);
custnum.setColumns(10);

JLabel lblNewLabel_8 = new JLabel("Customer No.:");
lblNewLabel_8.setBounds(10, 27, 91, 14);
frame.getContentPane().add(lblNewLabel_8);
}
}
