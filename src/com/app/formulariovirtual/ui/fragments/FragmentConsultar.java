package com.app.formulariovirtual.ui.fragments;

import com.app.formulariovirtual.R;
import com.app.formulariovirtual.sqlite.Articulos;
import com.app.formulariovirtual.sqlite.DatabaseHandler;
import com.app.formulariovirtual.ui.activities.MainActivity;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.FontAwesomeText;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

public class FragmentConsultar extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "consultar";

	private FontAwesomeText anim;
	private BootstrapEditText txtConsultar;
	private BootstrapEditText txtNombre;
	private BootstrapEditText txtTipoProducto;
	private BootstrapEditText txtColor;
	private BootstrapEditText txtMarca;
	private BootstrapEditText txtCantidad;
	private BootstrapButton btnConsultar;
	private BootstrapButton btnActualizar;
	private BootstrapButton btnBorrar;
	private LinearLayout panel;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FragmentConsultar newInstance(int sectionNumber) {
		FragmentConsultar fragment = new FragmentConsultar();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentConsultar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_fragment_consultar,
				container, false);

		anim = (FontAwesomeText) rootView.findViewById(R.id.lblTwo);
		anim.startRotate(getActivity(), true,
				FontAwesomeText.AnimationSpeed.SLOW);
		anim = (FontAwesomeText) rootView.findViewById(R.id.lblTwo);
		anim.startRotate(getActivity(), true,
				FontAwesomeText.AnimationSpeed.SLOW);

		txtConsultar = (BootstrapEditText) rootView
				.findViewById(R.id.txtbuscar);
		txtNombre = (BootstrapEditText) rootView.findViewById(R.id.txtNombre);
		txtTipoProducto = (BootstrapEditText) rootView
				.findViewById(R.id.txtTipo);
		txtColor = (BootstrapEditText) rootView.findViewById(R.id.txtColor);
		txtMarca = (BootstrapEditText) rootView.findViewById(R.id.txtMarca);
		txtCantidad = (BootstrapEditText) rootView
				.findViewById(R.id.txtCantidad);

		panel = (LinearLayout) rootView.findViewById(R.id.panel_principal);

		btnConsultar = (BootstrapButton) rootView.findViewById(R.id.btnguardar);
		btnConsultar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!TextUtils.isEmpty(txtConsultar.getText())) {

					consultarEnBD();

				} else {
					Toast.makeText(getActivity(),
							"Ningun campo puede ir vacio", 5).show();
				}

			}
		});

		btnActualizar = (BootstrapButton) rootView
				.findViewById(R.id.btnActualizar);
		btnActualizar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(txtConsultar.getText())
						&& !TextUtils.isEmpty(txtNombre.getText())
						&& !TextUtils.isEmpty(txtTipoProducto.getText())
						&& !TextUtils.isEmpty(txtColor.getText())
						&& !TextUtils.isEmpty(txtMarca.getText())
						&& !TextUtils.isEmpty(txtCantidad.getText())) {

					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(R.string.titulo_alerta_actualizar)
							.setPositiveButton(R.string.datos_insertados_ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											actualizarDatos();
											dialog.dismiss();

										}

									})
							.setNegativeButton(R.string.cancelar,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();

										}
									});
					builder.show();

				}

			}
		});

		btnBorrar = (BootstrapButton) rootView.findViewById(R.id.btnBorrar);
		btnBorrar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (!TextUtils.isEmpty(txtConsultar.getText())) {
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setMessage(R.string.titulo_alerta_borrar)
							.setPositiveButton(R.string.datos_insertados_ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											borrarRegistro();
											dialog.dismiss();

										}

									})
							.setNegativeButton(R.string.cancelar,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											dialog.dismiss();

										}
									});
					builder.show();
				}

			}
		});
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}

	protected void consultarEnBD() {
		/**
		 * Operaciones CRUD
		 * */

		DatabaseHandler dbh = new DatabaseHandler(getActivity());
		// Insertar datos
		Log.d("Consultar: ", "Consultando...");
		Articulos art = dbh.getArticulo(Integer.parseInt(txtConsultar.getText()
				.toString()));

		if (art != null) {
			panel.setVisibility(View.VISIBLE);
			txtNombre.setText(art.getNombre());
			txtTipoProducto.setText(art.getTipo());
			txtColor.setText(art.getColor());
			txtMarca.setText(art.getMarca());
			txtCantidad.setText(art.getCantidad());

		}

	}

	private void actualizarDatos() {
		/**
		 * Operaciones CRUD
		 * */

		DatabaseHandler dbh = new DatabaseHandler(getActivity());
		// Insertar datos
		Log.d("Actualizar: ", "Actualizando...");

		Articulos articulo = new Articulos();
		articulo.setId(Integer.parseInt(txtConsultar.getText().toString()));
		articulo.setNombre(txtNombre.getText().toString());
		articulo.setTipo(txtTipoProducto.getText().toString());
		articulo.setColor(txtColor.getText().toString());
		articulo.setMarca(txtMarca.getText().toString());
		articulo.setCantidad(txtCantidad.getText().toString());

		dbh.ActualizatArticulo(articulo);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.titulo_borrar_ok).setPositiveButton(
				R.string.datos_insertados_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						panel.setVisibility(View.GONE);
						dialog.dismiss();

					}
				});

		builder.show();
	}
	
	private void borrarRegistro(){
		/**
		 * Operaciones CRUD
		 * */

		DatabaseHandler dbh = new DatabaseHandler(getActivity());
		// Insertar datos
		Log.d("Borrar: ", "Borrando...");
		
		Articulos articulo = new Articulos();
		articulo.setId(Integer.parseInt(txtConsultar.getText().toString()));
		articulo.setNombre(txtNombre.getText().toString());
		articulo.setTipo(txtTipoProducto.getText().toString());
		articulo.setColor(txtColor.getText().toString());
		articulo.setMarca(txtMarca.getText().toString());
		articulo.setCantidad(txtCantidad.getText().toString());
		
		dbh.BorrarArticulo(articulo);
		
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.titulo_borrar_ok).setPositiveButton(
				R.string.datos_insertados_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						panel.setVisibility(View.GONE);
						dialog.dismiss();

					}
				});

		builder.show();
		
	}
}
