package edu.ucne.adrian_hernandez_bonilla_AP2_p1.ui.theme.Util


sealed class  UiEvent {
    data class ShowMessage(val message: String) : UiEvent()
    object NavigateBack : UiEvent()
    data class NavigateTo(val route: String) : UiEvent()


}