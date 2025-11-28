// This file will manage cart state and persist it across pages using localStorage

const STORAGE_KEY = "my_cart_items";

// Fetch cart from storage or return empty array
function getCart() {
  const data = localStorage.getItem(STORAGE_KEY);
  return data ? JSON.parse(data) : [];
}

// Save cart array to storage
function saveCart(cart) {
  localStorage.setItem(STORAGE_KEY, JSON.stringify(cart));
}

// Add item to cart (if exists increase qty, else add new)
function addToCart(item) {
  const cart = getCart();
  const existing = cart.find(x => x.id === item.id);
  if (existing) {
    existing.quantity++;
  } else {
    cart.push({ ...item, quantity: 1 });
  }
  saveCart(cart);
}

// Remove one quantity (or remove item if qty becomes 0)
function decrementFromCart(id) {
  const cart = getCart();
  const idx = cart.findIndex(x => x.id === id);
  if (idx > -1) {
    cart[idx].quantity--;
    if (cart[idx].quantity <= 0) {
      cart.splice(idx, 1);
    }
    saveCart(cart);
  }
}

// Remove item completely
function removeFromCart(id) {
  const cart = getCart().filter(x => x.id !== id);
  saveCart(cart);
}

// Clear all (if needed)
function clearCart() {
  localStorage.removeItem(STORAGE_KEY);
}

// Utility: format price
function formatPrice(price) {
  return "₹" + price;
}
