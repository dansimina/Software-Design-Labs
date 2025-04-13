interface TableProps<T> {
  data: Array<T>;
  columns: Array<{ header: string; accessor: keyof T }>;
  onRowClick?: (row: T) => void;
}

export function Table<T>({ data, columns, onRowClick }: TableProps<T>) {
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
              onClick={() => onRowClick && onRowClick(row)}
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
