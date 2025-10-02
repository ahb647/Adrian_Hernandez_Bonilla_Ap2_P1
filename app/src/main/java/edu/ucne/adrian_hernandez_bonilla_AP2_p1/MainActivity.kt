package edu.ucne.adrian_hernandez_bonilla_AP2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.Domain.Model.EntradaHuacales
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.Adrian_Hernandez_Bonilla_Ap2_P1Theme
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales_List.RegistroEntradaHuacalesListScreen
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales_List.RegistroEntradaHuacalesListViewModel
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.RegistroEntradaHuacales.RegistroEntradaHuacalesListOnlyScreen
import edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.Util.Route

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Adrian_Hernandez_Bonilla_Ap2_P1Theme {
                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }

                Scaffold(
                    topBar = { TopAppBar(title = { Text("Registro de Huacales") }) },
                    snackbarHost = { SnackbarHost(snackbarHostState) }
                ) { paddingValues ->

                    NavHost(
                        navController = navController,
                        startDestination = Route.ENTRADAHUACALES_FORM,
                        modifier = Modifier.padding(paddingValues)
                    ) {


                        composable(Route.ENTRADAHUACALES_FORM) {
                            val viewModel: RegistroEntradaHuacalesListViewModel = hiltViewModel()
                            RegistroEntradaHuacalesListScreen(
                                viewModel = viewModel,
                                onNavigateBack = { finish() },
                                onNavigateToList = { navController.navigate(Route.ENTRADAHUACALES_LIST) }
                            )
                        }


                        composable(Route.ENTRADAHUACALES_LIST) {
                            val viewModel: RegistroEntradaHuacalesListViewModel = hiltViewModel()
                            val entradas by viewModel.entradas.collectAsState()

                            RegistroEntradaHuacalesListOnlyScreen(
                                entradas = entradas,
                                onDelete = { viewModel.eliminarEntrada(it) },
                                onSaveEdit = { updatedEntrada ->
                                    viewModel.actualizarEntrada(updatedEntrada)
                                },
                                onNavigateBack = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
