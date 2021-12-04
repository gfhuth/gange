import pandas as pd
import numpy as np
import os

def selectSQLType(var):
    
    if isinstance(var, int) or \
       isinstance(var, np.int64):
        ret = 'integer'
    elif isinstance(var, str):
        ret = 'varchar'
    elif isinstance(var, float):
        ret = 'DOUBLE'
    else:
        ret = None
        print('error')
    return ret


filesCSV = os.listdir('csv/')
for file in filesCSV:
   
    #Cria tabela
    table_csv = pd.read_csv('csv/'+file)
    file = file[:-4]
    sql_file = open('sql/'+ file +'.sql', 'w')
    sql_file.write('CREATE TABLE '+ file +' (\n\t')
    for column in table_csv:
        sql_file.write(column + ' ')
        sql_file.write(selectSQLType(table_csv.iloc[0][column]) + '')
        if isinstance(table_csv.iloc[0][column], str):
            sql_file.write('(' + \
                  str(table_csv[column].str.len().max() + 8)+')')
        if (column == table_csv.columns[0]):
            sql_file.write(' NOT NULL' ) 
        sql_file.write(', \n\t')
    sql_file.write('PRIMARY KEY (' + table_csv.columns[0] +')\n);\n')
    
    
    #insere os elementos
    for row in range(len(table_csv)):
        sql_file.write('INSERT INTO '+ file +' VALUES (\n\t')
        for column in table_csv:
            sql_file.write('\'' + 
                  str(table_csv.iloc[row][column])+ '\'' )
            if (column != table_csv.columns[-1]):
                sql_file.write(', \n\t')
        sql_file.write('\n);\n')  
sql_file.close()