package database.management;

import java.sql.SQLException;
import java.util.LinkedList;

import saving.ISave;
import saving.SaveJson;
import saving.SaveXML;


public class ArgumentValidation implements IDataBaseValidation {
	public DataBaseManagement dbManage = new DataBaseManagement();
	private int dbUsed = -1;
	private String dbUsedName = null;
	private ISave obj;
	//private SaveXML obj = new SaveXML("ahmed");
	private LinkedList<LinkedList<String>> helpToSave = new LinkedList<LinkedList<String>>();
	private LinkedList<LinkedList<String>> selectedData = new LinkedList<LinkedList<String>>();
	private int affectedRows = 0;
	private String finalPath;
	private String finalSave;

	public ArgumentValidation(String path, String save) {
		this.finalPath = path;
		this.finalSave = save;
		setSaveMethod(this.finalSave);
	}

	private void setSaveMethod(String save) {
		if (save.equals("xml")) {
			obj = new SaveXML(this.finalPath);
		} else {
			obj = new SaveJson(this.finalPath);
		}

	}
	

	public int getIndexOfDbUsed() {
		return dbUsed;
	}

	public DataBaseManagement getDbManage() {
		return dbManage;
	}

	public LinkedList<LinkedList<String>> getSelectedLinkedList() {
		return selectedData;
	}

	@Override
	public void creatDataBase(String dbName) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		int index = dbManage.searchForDataBase(dbName);
		if (index == -1) {
			dbManage.createDataBase(dbName);
			obj.save(saveall(), helpToSave);

		} else {
			throw new SQLException("DataBase is duplicated");
		}

	}

	@Override
	public void creatTable(String tableName, LinkedList<String> columnsName, LinkedList<String> columnsType)
			throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index == -1) {
				dbManage.getDataBases().get(dbUsed).createTable(tableName);
				dbManage.getDataBases().get(dbUsed).getTables().getLast().createColumns(columnsName, columnsType);
				obj.save(saveall(), helpToSave);

			} else {

				throw new SQLException("Table is duplicated");
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}

	}

	@Override
	public void insertIntoTable(String tableName, LinkedList<String> values) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				if (values.size() == dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().size()) {

					dbManage.getDataBases().get(dbUsed).getTables().get(index).fillColumns(values);
					obj.save(saveall(), helpToSave);
					affectedRows = 1;
				} else {
					throw new SQLException("size of values is not equale of size of columns");
				}
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}
	}

	@Override
	public void selectAll(String tableName) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				LinkedList<Object> columns = new LinkedList<>();
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
						.size(); i++) {
					columns.add(i);
				}
				LinkedList<Object> rows = new LinkedList<>();
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
						.getValues().size(); i++) {
					rows.add(i);
				}
				selectedData = dbManage.getDataBases().get(dbUsed).getTables().get(index).selectedData(columns, rows);
				// dbManage.getDataBases().get(dbUsed).getTables().get(index).printColumns(columns);
			} else {
				throw new SQLException();
			}
		} else {

			throw new SQLException("you have to use the 'use' statement");
		}

	}

	@Override
	public void selectFromTable(String tableName, LinkedList<String> selectedColumn) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				LinkedList<Object> columns = new LinkedList<>();
				for (int i = 0; i < selectedColumn.size(); i++) {
					int indexOfColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
							.searchForColumn(selectedColumn.get(i));
					if (indexOfColumn == -1) {
						throw new SQLException("there is column not found");
						// return;
					} else {
						columns.add(indexOfColumn);
					}
				}
				LinkedList<Object> rows = new LinkedList<>();
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
						.getValues().size(); i++) {
					rows.add(i);
				}
				selectedData = dbManage.getDataBases().get(dbUsed).getTables().get(index).selectedData(columns, rows);
			} else {
				throw new SQLException();

			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}

	}

	@Override
	public void deleteFromTable(String tableName, String columnToBeChecked, String valueToBeChecked, String operator)
			throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexOfColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnToBeChecked);
				if (indexOfColumn != -1) {
					if (dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(indexOfColumn)
							.getType().validate(valueToBeChecked) == true) {
						LinkedList<Object> rows = dbManage.getDataBases().get(dbUsed).getTables().get(index)
								.getColumns().get(indexOfColumn).getRows(operator, valueToBeChecked);
						affectedRows = rows.size();
						dbManage.getDataBases().get(dbUsed).getTables().get(index).deleteRows(rows);
						obj.save(saveall(), helpToSave);

					} else {
						throw new SQLException("this value cannot to compare for invalid type");
					}
				} else {
					throw new SQLException("this column name is not found");
				}
			} else {
				throw new SQLException("this table name is not found");
			}
		}

	}

	@Override
	public void updateTable(String tableName, String conditionColumn, String operator, String conditionValue,
			LinkedList<String> setColumns, LinkedList<String> setValues) throws SQLException {
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		// TODO Auto-generated method stub
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexOfColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(conditionColumn);
				boolean check = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
						.get(indexOfColumn).getType().validate(conditionValue);

				if (indexOfColumn != -1 && check == true) {
					LinkedList<Object> selectedColumns = new LinkedList<>();
					// all index of selected columns

					for (int i = 0; i < setColumns.size(); i++) {

						int indexOfAnyColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
								.searchForColumn(setColumns.get(i));
						check = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
								.get(indexOfAnyColumn).getType().validate(setValues.get(i));
						if (indexOfAnyColumn == -1) {
							throw new SQLException("this column is not found");
						} else if (check == false) {

							throw new SQLException("this value cannot to compare for invalid type");
						}
						selectedColumns.add(indexOfAnyColumn);
					} // end of validations lets update
					LinkedList<Object> rows = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.get(indexOfColumn).getRows(operator, conditionValue);
					dbManage.getDataBases().get(dbUsed).getTables().get(index).updateRows(rows, setValues,
							selectedColumns);
					affectedRows = rows.size();
					obj.save(saveall(), helpToSave);

				} else {
					throw new SQLException("this column name is not found");
				}

			}
		} else {
			throw new SQLException("this table name is not found");
		}
	}

	@Override
	public void dropDataBase(String databaseName) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		int index = dbManage.searchForDataBase(databaseName);
		if (index == -1) {
			throw new SQLException("This Database is not found");
		} else {
			dbManage.dropDataBase(index);
			use(dbUsedName);
			obj.dropDatabase(databaseName);
			obj.save(saveall(), helpToSave);

		}

	}

	@Override
	public void dropTable(String tableName) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				dbManage.getDataBases().get(dbUsed).dropTable(index);
				obj.dropTable(dbUsedName, tableName);
				obj.save(saveall(), helpToSave);

			} else {
				throw new SQLException("Table name is not found");
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}
	}

	public void use(String name) {
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		dbUsed = -1;
		for (int i = 0; i < dbManage.getDataBases().size(); i++) {
			if (dbManage.getDataBases().get(i).getName().equals(name)) {
				dbUsed = i;
				dbUsedName = name;
			}
		}

	}

	@Override
	public void selectAllByCondition(String tableName, String columnName, String Value, String operator)
			throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexForColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnName);
				if (indexForColumn != -1) {
					LinkedList<Object> rows = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.get(indexForColumn).getRows(operator, Value);

					LinkedList<Object> columns = new LinkedList<>();
					for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.size(); i++) {
						columns.add(i);
					}
					selectedData = dbManage.getDataBases().get(dbUsed).getTables().get(index).selectedData(columns,
							rows);

				} else {
					throw new SQLException();

				}
			} else {
				throw new SQLException();

			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}

	}

	@Override
	public void selectFromByCondition(String tableName, String columnName, String Value, String operator,
			LinkedList<String> selectedColumns) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexForColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnName);
				if (indexForColumn != -1) {
					LinkedList<Object> rows = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.get(indexForColumn).getRows(operator, Value);

					LinkedList<Object> columns = new LinkedList<>();
					for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.size(); i++) {
						for (int ind = 0; ind < selectedColumns.size(); ind++) {
							if (dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(i).getName()
									.equals(selectedColumns.get(ind))) {
								columns.add(i);
							}
						}
					}
					selectedData = dbManage.getDataBases().get(dbUsed).getTables().get(index).selectedData(columns,
							rows);

				} else {
					throw new SQLException();
				}
			} else {
				throw new SQLException();
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}
	}

	@Override
	public void deleteAll(String tableName) throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
						.size(); i++) {
					for (int j = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(i)
							.getValues().size() - 1; j > -1; j--) {
						dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(i).getValues()
								.remove(j);

					}
					affectedRows++;

				}
				obj.save(saveall(), helpToSave);

			} else {
				throw new SQLException("Table name is not found");
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}
	}

	public LinkedList<LinkedList<LinkedList<String>>> saveall() {
		LinkedList<LinkedList<LinkedList<String>>> allTabls = new LinkedList<LinkedList<LinkedList<String>>>();
		helpToSave = new LinkedList<LinkedList<String>>();
		for (int i = 0; i < dbManage.getDataBases().size(); i++) {
			for (int j = 0; j < dbManage.getDataBases().get(i).getTables().size(); j++) {
				LinkedList<String> help = new LinkedList<>();
				LinkedList<LinkedList<String>> helpTable = new LinkedList<LinkedList<String>>();
				LinkedList<String> helpcolumn = new LinkedList<String>();
				helpcolumn.add(dbManage.getDataBases().get(i).getName());
				helpcolumn.add(dbManage.getDataBases().get(i).getTables().get(j).getName());
				help.add(dbManage.getDataBases().get(i).getName());
				help.add(dbManage.getDataBases().get(i).getTables().get(j).getName());
				for (int ind = 0; ind < dbManage.getDataBases().get(i).getTables().get(j).getColumns().size(); ind++) {
					help.add(dbManage.getDataBases().get(i).getTables().get(j).getColumns().get(ind).getName());
				}
				helpToSave.add(help);
				helpTable.addLast(helpcolumn);
				for (int k = 0; k < dbManage.getDataBases().get(i).getTables().get(j).getColumns().size(); k++) {
					helpcolumn = new LinkedList<String>();
					helpcolumn.add(
							dbManage.getDataBases().get(i).getTables().get(j).getColumns().get(k).getTheNameOfType());
					helpcolumn.add(dbManage.getDataBases().get(i).getTables().get(j).getColumns().get(k).getName());
					for (int l = 0; l < dbManage.getDataBases().get(i).getTables().get(j).getColumns().get(k)
							.getValues().size(); l++) {
						helpcolumn.add(dbManage.getDataBases().get(i).getTables().get(j).getColumns().get(k).getValues()
								.get(l));
					}
					helpTable.add(helpcolumn);
				}
				allTabls.add(helpTable);
			}
		}
		return allTabls;

	}

	public void load(LinkedList<LinkedList<LinkedList<String>>> allTabls) throws SQLException {
		for (int i = 0; i < allTabls.size(); i++) {
			creatDataBase(allTabls.get(i).get(0).get(0));
			use(allTabls.get(i).get(0).get(0));
			String tableName = allTabls.get(i).get(0).get(1);
			LinkedList<String> columnsName = new LinkedList<>();
			LinkedList<String> columnsType = new LinkedList<>();
			for (int j = 1; j < allTabls.get(i).size(); j++) {
				columnsType.add(allTabls.get(i).get(j).get(0));
				columnsName.add(allTabls.get(i).get(j).get(1));
			}
			creatTable(tableName, columnsName, columnsType);
			for (int k = 2; k < allTabls.get(i).get(1).size(); k++) { // values
				LinkedList<String> values = new LinkedList<>();
				for (int ind = 1; ind < allTabls.get(i).size(); ind++) { // columns
					values.add(allTabls.get(i).get(ind).get(k));
				}
				insertIntoTable(tableName, values);
			}

		}
		dbUsed = -1;
	}

	@Override
	public void addColumn(String tableName, String columnName, String columntype) throws SQLException {
		selectedData = new LinkedList<LinkedList<String>>();
		affectedRows = 0;
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexForColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnName);
				if (indexForColumn == -1) {
					LinkedList<String> columnNames = new LinkedList<>();
					columnNames.add(columnName);
					LinkedList<String> columnType = new LinkedList<>();
					columnType.add(columntype);
					dbManage.getDataBases().get(dbUsed).getTables().get(index).createColumns(columnNames, columnType);

					for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
							.getValues().size(); i++) {
						dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().getLast().getValues()
								.add(null);
					}
					obj.save(saveall(), helpToSave);
					affectedRows = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
							.getValues().size();

				} else
					throw new SQLException("dublicate column name");
			} else
				throw new SQLException("Table name is not found");

		} else
			throw new SQLException("you have to use the 'use' statement");

	}

	@Override
	public void dropColumn(String tableName, String columnName) throws SQLException {
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexForColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnName);
				if (indexForColumn != -1) {
					affectedRows = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
							.getValues().size();
					dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().remove(indexForColumn);
					obj.save(saveall(), helpToSave);

				} else {
					throw new SQLException("column Name Not Found");
				}
			} else {
				throw new SQLException("Table name is not found");
			}

		} else {
			throw new SQLException("you have to use the 'use' statement");
		}

	}

	@Override
	public void insertByColumn(String tableName, LinkedList<String> values, LinkedList<String> columnsName)
			throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				if (columnsName.size() == dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().size()
						&& columnsName.size() == values.size()) {
					LinkedList<String> arrangedValues = new LinkedList<>();
					for (int i = 0; i < values.size(); i++) {
						// columns size of the table
						boolean found = false;
						for (int k = 0; k < columnsName.size(); k++) {
							// columns name of the parameter
							if (dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(i).getName()
									.toLowerCase().equals(columnsName.get(k).toLowerCase())) {
								found = true;
								arrangedValues.add(values.get(k));
							}
						}
						if (found == false) {
							throw new SQLException("this column name not found");
							// return;
						}
					}
					insertIntoTable(tableName, arrangedValues);
					obj.save(saveall(), helpToSave);
					affectedRows = 1;

				} else {
					throw new SQLException("incorrect size");
				}
			} else
				throw new SQLException("Table name is not found");

		} else
			throw new SQLException("you have to use the 'use' statement");

	}

	@Override
	public void updateSelectedColumns(String tableName, LinkedList<String> values, LinkedList<String> columnsName)
			throws SQLException {
		// TODO Auto-generated method stub
		affectedRows = 0;
		selectedData = new LinkedList<LinkedList<String>>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				if (columnsName.size() <= dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().size()
						&& columnsName.size() == values.size()) {
					LinkedList<Object> columnsIndex = new LinkedList<>();

					for (int i = 0; i < columnsName.size(); i++) {

						int indexOfColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
								.searchForColumn(columnsName.get(i));
						boolean check = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
								.get(indexOfColumn).getType().validate(values.get(i));

						if (indexOfColumn != -1 && check == true) {

							columnsIndex.add(indexOfColumn);
						} else {
							throw new SQLException("this column name is not found");
							// return;
						}

					}
					LinkedList<Object> rows = new LinkedList<>();
					for (int j = 0; j < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
							.getValues().size(); j++) {
						rows.add(j);
					}
					dbManage.getDataBases().get(dbUsed).getTables().get(index).updateRows(rows, values, columnsIndex);
					obj.save(saveall(), helpToSave);
					affectedRows = rows.size();

				} else {
					throw new SQLException("incorrect size");
				}
			} else {
				throw new SQLException("Table name is not found");
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");

		}

	}

	public int getAffectedRows() {
		return affectedRows;
	}

	public void selectDistinct(String tableName, LinkedList<String> columnsNames) throws SQLException {
		selectedData = new LinkedList<LinkedList<String>>();
		affectedRows = 0;
		LinkedList<String> types = new LinkedList<>();
		LinkedList<Object> columns = new LinkedList<>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				for (int i = 0; i < columnsNames.size(); i++) {
					boolean found = false;
					for (int j = 0; j < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.size(); j++) {
						if (columnsNames.get(i).equals(dbManage.getDataBases().get(dbUsed).getTables().get(index)
								.getColumns().get(j).getName())) {
							found = true;
							columns.add(j);
							types.add(dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(j)
									.getTheNameOfType());
						}
					}
					if (!found) {
						throw new SQLException("there is a column is not found");
						// return;
					}
				}

				dbManage.getDataBases().get(dbUsed).createTable("first");
				dbManage.getDataBases().get(dbUsed).getTables().getLast().createColumns(columnsNames, types);
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(0)
						.getValues().size(); i++) {
					LinkedList<String> setValues = new LinkedList<>();
					for (int j = 0; j < columns.size(); j++) {
						setValues.add(dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
								.get((int) columns.get(j)).getValues().get(i));
					}
					dbManage.getDataBases().get(dbUsed).getTables().getLast().fillColumns(setValues);
				}
				LinkedList<Object> Indexofdeleterow = new LinkedList<>();
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns().get(0)
						.getValues().size() - 1; i++) {
					for (int j = i + 1; j < dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns()
							.get(0).getValues().size(); j++) {
						if (dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns().get(0).getValues()
								.get(i).equals(dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns()
										.get(0).getValues().get(j))) {
							boolean found = false;
							for (int k = 1; k < dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns()
									.size(); k++) {
								if (!dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns().get(k)
										.getValues().get(i).equals(dbManage.getDataBases().get(dbUsed).getTables()
												.getLast().getColumns().get(k).getValues().get(j))) {
									found = true;
									break;
								}
							}
							if (!found) {
								boolean repeat = false;
								for (int u = 0; u < Indexofdeleterow.size(); u++) {
									if (Indexofdeleterow.get(u).equals(j))
										repeat = true;
								}
								if (!repeat)
									Indexofdeleterow.add(j);
							}

						}
					}
				}
				dbManage.getDataBases().get(dbUsed).getTables().getLast().deleteRows(Indexofdeleterow);
				LinkedList<Object> selectedColumns = new LinkedList<>();
				for (int i = 0; i < dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns()
						.size(); i++) {
					selectedColumns.add(i);
				}
				LinkedList<Object> rows = new LinkedList<>();
				for (int j = 0; j < dbManage.getDataBases().get(dbUsed).getTables().getLast().getColumns().get(0)
						.getValues().size(); j++) {
					rows.add(j);
				}

				selectedData = dbManage.getDataBases().get(dbUsed).getTables().getLast().selectedData(selectedColumns,
						rows);
				dbManage.getDataBases().get(dbUsed).getTables().removeLast();

			} else
				throw new SQLException();
		} else
			throw new SQLException("you have to use the 'use' statement");

	}

	@Override
	public void selectDistinctByCondition(String tableName, LinkedList<String> columnsNames, String columnName,
			String Value, String operator) throws SQLException {
		affectedRows = 0;
		LinkedList<String> types = new LinkedList<>();
		LinkedList<Object> columns = new LinkedList<>();
		if (dbUsed != -1) {
			int index = dbManage.getDataBases().get(dbUsed).searchForTable(tableName);
			if (index != -1) {
				int indexForColumn = dbManage.getDataBases().get(dbUsed).getTables().get(index)
						.searchForColumn(columnName);
				if (indexForColumn != -1) {
					LinkedList<Object> rows = dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
							.get(indexForColumn).getRows(operator, Value);

					for (int i = 0; i < columnsNames.size(); i++) {
						boolean found = false;
						for (int j = 0; j < dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
								.size(); j++) {
							if (columnsNames.get(i).equals(dbManage.getDataBases().get(dbUsed).getTables().get(index)
									.getColumns().get(j).getName())) {
								found = true;
								columns.add(j);
								types.add(dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns().get(j)
										.getTheNameOfType());
							}
						}
						if (!found) {
							throw new SQLException("there is a column is not found");
							// return;
						}
					}

					dbManage.getDataBases().get(dbUsed).createTable("second");
					dbManage.getDataBases().get(dbUsed).getTables().getLast().createColumns(columnsNames, types);

					for (int i = 0; i < rows.size(); i++) {
						LinkedList<String> setValues = new LinkedList<>();
						for (int j = 0; j < columns.size(); j++) {
							setValues.add(dbManage.getDataBases().get(dbUsed).getTables().get(index).getColumns()
									.get((int) columns.get(j)).getValues().get((int) rows.get(i)));
						}
						dbManage.getDataBases().get(dbUsed).getTables().getLast().fillColumns(setValues);
					}
					selectDistinct("second", columnsNames);
					dbManage.getDataBases().get(dbUsed).getTables().removeLast();

				} else {
					throw new SQLException();
				}
			} else {
				throw new SQLException();
			}
		} else {
			throw new SQLException("you have to use the 'use' statement");
		}

	}

}
