package cl.duoc.dsy.huertohogar.viewmodel

import cl.duoc.dsy.huertohogar.model.CartItem

data class CartState(
    val items: List<CartItem> = emptyList(),
    val totalPrice: Double = 0.0
)