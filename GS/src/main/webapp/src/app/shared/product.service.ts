import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Product} from './product.model';
import {User} from "./user.model";
import {Cart} from "./cart.model";

@Injectable({providedIn: 'root'})
export class ProductService {
  private productUrl = 'http://localhost:8080/product';
  private product: Product[];

  constructor(private router: Router, private http: HttpClient) {
  }

  getProducts(): Observable<Product[]>
  {
    return this.http.get<Product[]>(this.productUrl + '/getProducts');
  }

  getCartProductsForUser(user: User): Observable<Product[]>
  {
    return this.http.post<Product[]>(this.productUrl + '/getCartProductsForUser', user);
  }

  addProductToCart(cart: Cart): Observable<Cart>{
    return this.http.post<Cart>(this.productUrl + '/addProductToCart', cart);
  }

  deleteProductFromCart(cartID:number){
    this.http.delete(this.productUrl + 'deleteCart/' + cartID);
  }
}
