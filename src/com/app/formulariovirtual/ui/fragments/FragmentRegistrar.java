package com.app.formulariovirtual.ui.fragments;

import java.util.List;

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
import android.widget.Toast;

import com.app.formulariovirtual.R;
import com.app.formulariovirtual.sqlite.Articulos;
import com.app.formulariovirtual.sqlite.DatabaseHandler;
import com.app.formulariovirtual.ui.activities.MainActivity;
import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.FontAwesomeText;

public class FragmentRegistrar extends Fragment {

	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "registrar";

	private FontAwesomeText anim;
	private BootstrapEditText txtNombre;
	private BootstrapEditText txtTipoProducto;
	private BootstrapEditText txtColor;
	private BootstrapEditText txtMarca;
	private BootstrapEditText txtCantidad;
	private BootstrapButton btnGuardar;

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	public static FragmentRegistrar newInstance(int sectionNumber) {
		FragmentRegistrar fragment = new FragmentRegistrar();
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		fragment.setArguments(args);
		return fragment;
	}

	public FragmentRegistrar() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);

		anim = (FontAwesomeText) rootView.findViewById(R.id.lblTwo);
		anim.startRotate(getActivity(), true,
				FontAwesomeText.AnimationSpeed.SLOW);

		txtNombre = (BootstrapEditText) rootView.findViewById(R.id.txtNombre);
		txtTipoProducto = (BootstrapEditText) rootView
				.findViewById(R.id.txtTipo);
		txtColor = (BootstrapEditText) rootView.findViewById(R.id.txtColor);
		txtMarca = (BootstrapEditText) rootView.findViewById(R.id.txtMarca);
		txtCantidad = (BootstrapEditText) rootView
				.findViewById(R.id.txtCantidad);

		btnGuardar = (BootstrapButton) rootView.findViewById(R.id.btnguardar);
		btnGuardar.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (!TextUtils.isEmpty(txtNombre.getText())
						&& !TextUtils.isEmpty(txtTipoProducto.getText())
						&& !TextUtils.isEmpty(txtColor.getText())
						&& !TextUtils.isEmpty(txtMarca.getText())
						&& !TextUtils.isEmpty(txtCantidad.getText())) {

					Articulos articulo = new Articulos();
					articulo.setNombre(txtNombre.getText().toString());
					articulo.setTipo(txtTipoProducto.getText().toString());
					articulo.setColor(txtColor.getText().toString());
					articulo.setMarca(txtMarca.getText().toString());
					articulo.setCantidad(txtCantidad.getText().toString());

					guardarDatosEnBD(articulo);

				} else {
					Toast.makeText(getActivity(),
							"Ningun campo puede ir vacio", 5).show();
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

	public void guardarDatosEnBD(Articulos articulo) {

		DatabaseHandler dbh = new DatabaseHandler(getActivity());
		/**
		 * Operaciones CRUD
		 * */
		// Insertar datos
		Log.d("Insertar: ", "Insertando...");
		dbh.addArticulo(articulo);

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(R.string.titulo_alerta).setPositiveButton(
				R.string.datos_insertados_ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						limpiarCampos();
						dialog.dismiss();

					}
				});

		builder.show();
		// leer toda la data
		Log.d("Leyendo: ", "Leyendo todos los datos..");
		List<Articulos> articulos = dbh.getAllArticles();

		for (Articulos ar : articulos) {
			String log = "ID: " + ar.getId() + " ,Nombre: " + ar.getNombre()
					+ " ,Tipo: " + ar.getTipo() + " ,Color: " + ar.getColor()
					+ " ,Marca: " + ar.getMarca() + " ,Cantidad: "
					+ ar.getCantidad();
			// Writing Contacts to log
			Log.d("Name: ", log);
		}

	}

	public void limpiarCampos() {
		txtNombre.setText("");
		txtTipoProducto.setText("");
		txtColor.setText("");
		txtMarca.setText("");
		txtCantidad.setText("");
	}
}
