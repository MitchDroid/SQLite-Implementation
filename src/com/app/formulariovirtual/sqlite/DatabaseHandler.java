package com.app.formulariovirtual.sqlite;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// Todas las variables estaticas
	// Version de la base de datos
	private static final int DATABASE_VERSION = 1;

	// Nombre de la base de datos
	private static final String DATABASE_NAME = "articulos";

	// Nombre de la tabla tb_articulos
	private static final String TABLA_ARTICULOS = "tb_articulos";

	// Columnas de la tabla articulos
	private static final String KEY_ID = "id";
	private static final String KEY_NOMBRE = "nombre";
	private static final String KEY_TIPO = "tipo";
	private static final String KEY_COLOR = "color";
	private static final String KEY_MARCA = "marca";
	private static final String KEY_CANT = "cantidad";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// CREANDO LA TABLA
	@Override
	public void onCreate(SQLiteDatabase db) {
		String CREAR_TABLA_ARTICULOS = "CREATE TABLE " + TABLA_ARTICULOS + "("
				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NOMBRE + " TEXT,"
				+ KEY_TIPO + " TEXT," + KEY_COLOR + " TEXT," + KEY_MARCA
				+ " TEXT," + KEY_CANT + " TEXT" + ")";

		db.execSQL(CREAR_TABLA_ARTICULOS);

	}

	// actualizacion de la base de datos
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Eliminar la tabla anterior si existe
		db.execSQL("DROP TABLE IF EXIST " + TABLA_ARTICULOS);

		// Crea tablas nuevamente
		onCreate(db);

	}

	// Agregar nuevo producto
	public void addArticulo(Articulos articulo) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_NOMBRE, articulo.getNombre());
		values.put(KEY_TIPO, articulo.getTipo());
		values.put(KEY_COLOR, articulo.getColor());
		values.put(KEY_MARCA, articulo.getMarca());
		values.put(KEY_CANT, articulo.getCantidad());

		// Insertar fila
		db.insert(TABLA_ARTICULOS, null, values);
		
		// cierra conexion a bd
		db.close();
	}

	// obtener Articulo
	public Articulos getArticulo(int id) {
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.query(TABLA_ARTICULOS, new String[] { KEY_ID,
				KEY_NOMBRE, KEY_TIPO, KEY_COLOR, KEY_MARCA, KEY_CANT }, KEY_ID
				+ "=?", new String[] { String.valueOf(id) }, null, null, null,
				null);
		if (cursor != null)
			cursor.moveToFirst();

		Articulos articulo = new Articulos(
				Integer.parseInt(cursor.getString(0)), cursor.getString(1),
				cursor.getString(2), cursor.getString(3), cursor.getString(4),
				cursor.getString(5));

		return articulo;

	}

	// Obtener todos los articulos
	public List<Articulos> getAllArticles() {
		List<Articulos> listaArticulos = new ArrayList<Articulos>();
		// Seleccionar todos lo elementos de la tabla
		String selectQuery = "SELECT * FROM " + TABLA_ARTICULOS;

		SQLiteDatabase db = this.getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		// recorrer toda los elementos y agregarlos a la lista
		if (cursor.moveToFirst()) {
			do {
				Articulos articulo = new Articulos();
				articulo.setId(Integer.parseInt(cursor.getString(0)));
				articulo.setNombre(cursor.getString(1));
				articulo.setTipo(cursor.getString(2));
				articulo.setColor(cursor.getString(3));
				articulo.setMarca(cursor.getString(4));
				articulo.setCantidad(cursor.getString(5));
				
				//agregar articulos a la lista
				listaArticulos.add(articulo);
			} while (cursor.moveToNext());

		}

		return listaArticulos;
	}

	// Obtener el tamaño
	public int getContactsCount() {
		String contadorQuery = "SELECT * FROM " + TABLA_ARTICULOS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(contadorQuery, null);
		cursor.close();

		return cursor.getCount();
	}

	// Actualizar articulo
	public int ActualizatArticulo(Articulos articulo) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_NOMBRE, articulo.getNombre());
		values.put(KEY_TIPO, articulo.getTipo());
		values.put(KEY_COLOR, articulo.getColor());
		values.put(KEY_MARCA, articulo.getMarca());
		values.put(KEY_CANT, articulo.getCantidad());

		// actualizar fila
		return db.update(TABLA_ARTICULOS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(articulo.getId()) });

	}

	// Borrar un articulo
	public void BorrarArticulo(Articulos articulo) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLA_ARTICULOS, KEY_ID + " = ?",
				new String[] { String.valueOf(articulo.getId()) });
		db.close();

	}

}
