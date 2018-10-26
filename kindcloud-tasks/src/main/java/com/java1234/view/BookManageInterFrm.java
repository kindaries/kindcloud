/*
 * BookManageInterFrm.java
 *
 * Created on __DATE__, __TIME__
 */

package com.java1234.view;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Vector;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import com.java1234.dao.BookDao;
import com.java1234.dao.BookTypeDao;
import com.java1234.model.Book;
import com.java1234.model.BookType;
import com.java1234.util.DbUtil;
import com.java1234.util.StringUtil;

/**
 *
 * @author  __USER__
 */
public class BookManageInterFrm extends javax.swing.JInternalFrame {
	DbUtil dbUtil = new DbUtil();
	BookTypeDao bookTypeDao = new BookTypeDao();
	BookDao bookDao = new BookDao();

	/** Creates new form BookManageInterFrm */
	public BookManageInterFrm() {
		initComponents();
		this.setLocation(200, 50);
		this.fillTable(new Book());
		this.fillBookType("search");
		this.s_jrbman.setSelected(true);
		this.jrb_man.setSelected(true);
		this.fillBookType("modify");
	}

	private void fillBookType(String type) {
		Connection con = null;
		BookType bookType = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookTypeDao.bookTypeList(con, new BookType());
			if ("search".equals(type)) {
				bookType = new BookType();
				bookType.setBookTypeName("��ѡ��...");
				bookType.setId(-1);
				this.s_jcbBookType.addItem(bookType);
			}
			while (rs.next()) {
				bookType = new BookType();
				bookType.setId(rs.getInt("id"));
				bookType.setBookTypeName(rs.getString("bookTypeName"));
				if ("search".equals(type)) {
					this.s_jcbBookType.addItem(bookType);
				} else if ("modify".equals(type)) {
					this.jcb_bookType.addItem(bookType);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void fillTable(Book book) {
		DefaultTableModel dtm = (DefaultTableModel) bookTable.getModel();
		dtm.setRowCount(0);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			ResultSet rs = bookDao.bookList(con, book);
			while (rs.next()) {
				Vector v = new Vector();
				v.add(rs.getInt("id"));
				v.add(rs.getString("bookName"));
				v.add(rs.getString("author"));
				v.add(rs.getString("sex"));
				v.add(rs.getFloat("price"));
				v.add(rs.getString("bookDesc"));
				v.add(rs.getString("bookTypeName"));
				dtm.addRow(v);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	//GEN-BEGIN:initComponents
	// <editor-fold defaultstate="collapsed" desc="Generated Code">
	private void initComponents() {

buttonGroup1 = new javax.swing.ButtonGroup();
buttonGroup2 = new javax.swing.ButtonGroup();
jScrollPane1 = new javax.swing.JScrollPane();
bookTable = new javax.swing.JTable();
jPanel1 = new javax.swing.JPanel();
jLabel1 = new javax.swing.JLabel();
s_bookNameTxt = new javax.swing.JTextField();
jLabel2 = new javax.swing.JLabel();
s_authorTxt = new javax.swing.JTextField();
jLabel3 = new javax.swing.JLabel();
s_jrbman = new javax.swing.JRadioButton();
s_jrbfemale = new javax.swing.JRadioButton();
jLabel4 = new javax.swing.JLabel();
s_jcbBookType = new javax.swing.JComboBox();
jb_search = new javax.swing.JButton();
jPanel2 = new javax.swing.JPanel();
jLabel5 = new javax.swing.JLabel();
idTxt = new javax.swing.JTextField();
jLabel6 = new javax.swing.JLabel();
priceTxt = new javax.swing.JTextField();
jLabel7 = new javax.swing.JLabel();
bookNameTxt = new javax.swing.JTextField();
jLabel8 = new javax.swing.JLabel();
authorTxt = new javax.swing.JTextField();
jLabel9 = new javax.swing.JLabel();
jrb_man = new javax.swing.JRadioButton();
jrb_female = new javax.swing.JRadioButton();
jLabel10 = new javax.swing.JLabel();
jcb_bookType = new javax.swing.JComboBox();
jLabel11 = new javax.swing.JLabel();
jScrollPane2 = new javax.swing.JScrollPane();
bookDescTxt = new javax.swing.JTextArea();
jb_modify = new javax.swing.JButton();
jb_delete = new javax.swing.JButton();

setClosable(true);
setIconifiable(true);
setTitle("\u56fe\u4e66\u7ef4\u62a4");

bookTable.setModel(new DefaultTableModel(
	new Object [][] {
		
	},
	new String [] {
		"���", "ͼ������", "ͼ������", "�����Ա�", "ͼ��۸�", "ͼ������", "ͼ�����"
	}
) {
	boolean[] canEdit = new boolean [] {
		false, false, false, false, false, false, false
	};

	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return canEdit [columnIndex];
	}
});
bookTable.addMouseListener(new java.awt.event.MouseAdapter() {
public void mousePressed(java.awt.event.MouseEvent evt) {
bookTableMousePressed(evt);
}
});
jScrollPane1.setViewportView(bookTable);

jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("\u641c\u7d22\u6761\u4ef6"));

jLabel1.setText("\u56fe\u4e66\u540d\u79f0\uff1a");

jLabel2.setText("\u56fe\u4e66\u4f5c\u8005\uff1a");

jLabel3.setText("\u4f5c\u8005\u6027\u522b\uff1a");

buttonGroup1.add(s_jrbman);
s_jrbman.setText("\u7537");

buttonGroup1.add(s_jrbfemale);
s_jrbfemale.setText("\u5973");

jLabel4.setText("\u56fe\u4e66\u7c7b\u522b\uff1a");


jb_search.setIcon(new javax.swing.ImageIcon("C:\\Users\\caofeng\\Desktop\\images\\search.png")); // NOI18N
jb_search.setText("\u67e5\u8be2");
jb_search.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jb_searchActionPerformed(evt);
}
});

javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
jPanel1.setLayout(jPanel1Layout);
jPanel1Layout.setHorizontalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel1Layout.createSequentialGroup()
.addContainerGap()
.addComponent(jLabel1)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(s_bookNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(27, 27, 27)
.addComponent(jLabel2)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(s_authorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(26, 26, 26)
.addComponent(jLabel3)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(s_jrbman, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(s_jrbfemale, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jLabel4)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(s_jcbBookType, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(27, 27, 27)
.addComponent(jb_search)
.addGap(39, 39, 39))
);
jPanel1Layout.setVerticalGroup(
jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel1Layout.createSequentialGroup()
.addContainerGap()
.addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel1)
.addComponent(s_bookNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel2)
.addComponent(s_authorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel3)
.addComponent(s_jrbman)
.addComponent(s_jrbfemale)
.addComponent(jLabel4)
.addComponent(s_jcbBookType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jb_search))
.addContainerGap(27, Short.MAX_VALUE))
);

jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("\u8868\u5355\u64cd\u4f5c"));

jLabel5.setText("\u7f16\u53f7\uff1a");

idTxt.setEditable(false);

jLabel6.setText("\u4ef7\u683c\uff1a");

jLabel7.setText("\u56fe\u4e66\u540d\u79f0\uff1a");

jLabel8.setText("\u56fe\u4e66\u4f5c\u8005\uff1a");

jLabel9.setText("\u4f5c\u8005\u6027\u522b\uff1a");

buttonGroup2.add(jrb_man);
jrb_man.setText("\u7537");

buttonGroup2.add(jrb_female);
jrb_female.setText("\u5973");

jLabel10.setText("\u56fe\u4e66\u7c7b\u522b\uff1a");


jLabel11.setText("\u56fe\u4e66\u63cf\u8ff0\uff1a");

bookDescTxt.setColumns(20);
bookDescTxt.setRows(5);
jScrollPane2.setViewportView(bookDescTxt);

jb_modify.setIcon(new javax.swing.ImageIcon("C:\\Users\\caofeng\\Desktop\\images\\modify.png")); // NOI18N
jb_modify.setText("\u4fee\u6539");
jb_modify.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jb_modifyActionPerformed(evt);
}
});

jb_delete.setIcon(new javax.swing.ImageIcon("C:\\Users\\caofeng\\Desktop\\images\\delete.png")); // NOI18N
jb_delete.setText("\u5220\u9664");
jb_delete.addActionListener(new java.awt.event.ActionListener() {
public void actionPerformed(java.awt.event.ActionEvent evt) {
jb_deleteActionPerformed(evt);
}
});

javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
jPanel2.setLayout(jPanel2Layout);
jPanel2Layout.setHorizontalGroup(
jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addGap(24, 24, 24)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
.addComponent(jLabel6)
.addComponent(jLabel5))
.addGap(18, 18, 18)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
.addComponent(priceTxt)
.addComponent(idTxt, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE))
.addGap(27, 27, 27)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jLabel7)
.addGap(18, 18, 18)
.addComponent(bookNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jLabel8)
.addGap(18, 18, 18)
.addComponent(authorTxt)))
.addGap(33, 33, 33)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jLabel9)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jrb_man, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jrb_female, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
.addGap(42, 42, 42)
.addComponent(jLabel11))
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jLabel10)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jcb_bookType, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE))
.addGroup(jPanel2Layout.createSequentialGroup()
.addComponent(jb_modify)
.addGap(72, 72, 72)
.addComponent(jb_delete)))
.addContainerGap())
);
jPanel2Layout.setVerticalGroup(
jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(jPanel2Layout.createSequentialGroup()
.addGap(23, 23, 23)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel5)
.addComponent(idTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel7)
.addComponent(bookNameTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
.addGap(45, 45, 45)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel6)
.addComponent(priceTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addComponent(jLabel8)
.addComponent(authorTxt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
.addGroup(jPanel2Layout.createSequentialGroup()
.addGap(24, 24, 24)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel9)
.addComponent(jrb_man)
.addComponent(jrb_female)
.addComponent(jLabel11))
.addGap(45, 45, 45)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jLabel10)
.addComponent(jcb_bookType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
.addGroup(jPanel2Layout.createSequentialGroup()
.addGap(24, 24, 24)
.addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
.addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
.addComponent(jb_modify)
.addComponent(jb_delete)))
);

javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
getContentPane().setLayout(layout);
layout.setHorizontalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addContainerGap()
.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
.addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 900, Short.MAX_VALUE)
.addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
.addContainerGap())
);
layout.setVerticalGroup(
layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
.addGroup(layout.createSequentialGroup()
.addContainerGap()
.addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
.addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
.addGap(24, 24, 24))
);

pack();
}// </editor-fold>

	//GEN-END:initComponents
	private void jb_modifyActionPerformed(java.awt.event.ActionEvent evt) {
		String id = this.idTxt.getText();
		if (StringUtil.isEmpty(id)) {
			JOptionPane.showMessageDialog(null, "��ѡ��Ҫ�޸ĵļ�¼��");
			return;
		}
		
		String bookName=this.bookNameTxt.getText();
		String author=this.authorTxt.getText();
		String price=this.priceTxt.getText();
		String bookDesc=this.bookDescTxt.getText();
		
		if(StringUtil.isEmpty(bookName)){
			JOptionPane.showMessageDialog(null, "ͼ�����Ʋ���Ϊ�գ�");
			return;
		}
		if(StringUtil.isEmpty(author)){
			JOptionPane.showMessageDialog(null, "ͼ�����߲���Ϊ�գ�");
			return;
		}
		if(StringUtil.isEmpty(price)){
			JOptionPane.showMessageDialog(null, "ͼ��۸���Ϊ�գ�");
			return;
		}
		
		
		String sex="";
		if(this.jrb_man.isSelected()){
			sex="��";
		}else if(this.jrb_female.isSelected()){
			sex="Ů";
		}
		
		BookType bookType=(BookType) this.jcb_bookType.getSelectedItem();
		int bookTypeId=bookType.getId();
		
		
		
		Book book=new Book(Integer.parseInt(id),bookName,author,sex,Float.parseFloat(price), bookDesc, bookTypeId);
		Connection con = null;
		try {
			con = dbUtil.getCon();
			int modifyNum = bookDao.bookModify(con, book);
			if (modifyNum == 1) {
				JOptionPane.showMessageDialog(null, "�޸ĳɹ�");
				this.resetValue();
				this.fillTable(new Book());
			} else {
				JOptionPane.showMessageDialog(null, "�޸�ʧ��");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "�޸�ʧ��");
		} finally {
			try {
				dbUtil.closeCon(con);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void jb_deleteActionPerformed(java.awt.event.ActionEvent evt) {
		String id = this.idTxt.getText();
		if (StringUtil.isEmpty(id)) {
			JOptionPane.showMessageDialog(null, "��ѡ��Ҫɾ���ļ�¼��");
			return;
		}
		int n = JOptionPane.showConfirmDialog(null, "ȷ��Ҫɾ��������¼��");
		if (n == 0) {
			Connection con = null;
			try {
				con = dbUtil.getCon();
				int deleteNum = bookDao.bookDelete(con, id);
				if (deleteNum == 1) {
					JOptionPane.showMessageDialog(null, "ɾ���ɹ�");
					this.resetValue();
					this.fillTable(new Book());
				} else {
					JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "ɾ��ʧ��");
			} finally {
				try {
					dbUtil.closeCon(con);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void resetValue() {
		this.idTxt.setText("");
		this.bookNameTxt.setText("");
		this.authorTxt.setText("");
		this.jrb_man.setSelected(true);
		this.priceTxt.setText("");
		this.bookDescTxt.setText("");
		if (this.jcb_bookType.getItemCount() > 0) {
			this.jcb_bookType.setSelectedIndex(0);
		}
	}

	private void bookTableMousePressed(java.awt.event.MouseEvent evt) {
		// ��ȡѡ�е���
		int row = this.bookTable.getSelectedRow();
		this.idTxt.setText((Integer) bookTable.getValueAt(row, 0) + "");
		this.bookNameTxt.setText((String) bookTable.getValueAt(row, 1));
		this.authorTxt.setText((String) bookTable.getValueAt(row, 2));
		String sex = (String) bookTable.getValueAt(row, 3);
		if ("��".equals(sex)) {
			this.jrb_man.setSelected(true);
		} else if ("Ů".equals(sex)) {
			this.jrb_female.setSelected(true);
		}
		this.priceTxt.setText((Float) bookTable.getValueAt(row, 4) + "");
		this.bookDescTxt.setText((String) bookTable.getValueAt(row, 5));
		String bookTypeName = (String) bookTable.getValueAt(row, 6);
		int n = this.jcb_bookType.getItemCount();
		for (int i = 0; i < n; i++) {
			BookType item = (BookType) this.jcb_bookType.getItemAt(i);
			if (item.getBookTypeName().equals(bookTypeName)) {
				this.jcb_bookType.setSelectedIndex(i);
			}
		}
	}

	private void jb_searchActionPerformed(java.awt.event.ActionEvent evt) {
		String bookName = this.s_bookNameTxt.getText();
		String author = this.s_authorTxt.getText();
		String sex = "";
		if (this.s_jrbman.isSelected()) {
			sex = "��";
		} else if (this.s_jrbfemale.isSelected()) {
			sex = "Ů";
		}
		BookType bookType = (BookType) this.s_jcbBookType.getSelectedItem();
		int bookTypeId = bookType.getId();

		Book book = new Book(bookName, author, sex, bookTypeId);

		this.fillTable(book);
		this.resetValue();

	}

	//GEN-BEGIN:variables
	// Variables declaration - do not modify
	private javax.swing.JTextField authorTxt;
	private javax.swing.JTextArea bookDescTxt;
	private javax.swing.JTextField bookNameTxt;
	private javax.swing.JTable bookTable;
	private javax.swing.ButtonGroup buttonGroup1;
	private javax.swing.ButtonGroup buttonGroup2;
	private javax.swing.JTextField idTxt;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JLabel jLabel10;
	private javax.swing.JLabel jLabel11;
	private javax.swing.JLabel jLabel2;
	private javax.swing.JLabel jLabel3;
	private javax.swing.JLabel jLabel4;
	private javax.swing.JLabel jLabel5;
	private javax.swing.JLabel jLabel6;
	private javax.swing.JLabel jLabel7;
	private javax.swing.JLabel jLabel8;
	private javax.swing.JLabel jLabel9;
	private javax.swing.JPanel jPanel1;
	private javax.swing.JPanel jPanel2;
	private javax.swing.JScrollPane jScrollPane1;
	private javax.swing.JScrollPane jScrollPane2;
	private javax.swing.JButton jb_delete;
	private javax.swing.JButton jb_modify;
	private javax.swing.JButton jb_search;
	private javax.swing.JComboBox jcb_bookType;
	private javax.swing.JRadioButton jrb_female;
	private javax.swing.JRadioButton jrb_man;
	private javax.swing.JTextField priceTxt;
	private javax.swing.JTextField s_authorTxt;
	private javax.swing.JTextField s_bookNameTxt;
	private javax.swing.JComboBox s_jcbBookType;
	private javax.swing.JRadioButton s_jrbfemale;
	private javax.swing.JRadioButton s_jrbman;
	// End of variables declaration//GEN-END:variables

}