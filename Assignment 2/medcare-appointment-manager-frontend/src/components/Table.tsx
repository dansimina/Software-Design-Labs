import { useState } from "react";

interface TableProps<T> {
  data: Array<T>;
  columns: Array<{ header: string; accessor: keyof T }>;
  onRowClick?: (row: T) => void;
}

export function Table<T>({ data, columns, onRowClick }: TableProps<T>) {
  const [selectedRowIndex, setSelectedRowIndex] = useState<number | null>(null);

  const handleRowClick = (row: T, index: number) => {
    setSelectedRowIndex(index);
    if (onRowClick) {
      onRowClick(row);
    }
  };

  return (
    <div className="table-responsive mx-auto" style={{ maxWidth: "600px" }}>
      <table className="table table-bordered">
        <thead className="thead-light">
          <tr>
            {columns.map((column, index) => (
              <th key={index}>{column.header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {data.map((row, rowIndex) => (
            <tr
              key={rowIndex}
              onClick={() => handleRowClick(row, rowIndex)}
              className={selectedRowIndex === rowIndex ? "table-primary" : ""}
              style={{ cursor: onRowClick ? "pointer" : "default" }}
            >
              {columns.map((column, colIndex) => (
                <td key={colIndex}>{String(row[column.accessor])}</td>
              ))}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default Table;
