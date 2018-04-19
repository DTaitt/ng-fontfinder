import { Component, OnInit } from '@angular/core';
import { Http } from '@angular/http';

@Component({
  selector: 'app-card-display',
  templateUrl: './card-display.component.html',
  styleUrls: ['./card-display.component.css']
})
export class CardDisplayComponent implements OnInit {

  constructor(private http:Http) { }

  fonts:Object[];

  ngOnInit() {
    this.http.get("https://www.googleapis.com/webfonts/v1/webfonts?sort=popularity&key= AIzaSyAOVSz0lHeFAs7ll5LO6HTADinYVxy1vt4")
    .toPromise()
    .then(res => {
      this.fonts = res.json().items.slice(0, 36);
      console.log(res.json().items[0])
    })
  }

}
