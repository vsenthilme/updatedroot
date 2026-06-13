// multi-select-with-select-all.component.ts
import { Component, Input, Output, EventEmitter } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'app-multi-select-with-select-all',
  templateUrl: './multi-select-with-select-all.component.html',
  styleUrls: ['./multi-select-with-select-all.component.css']
})
export class MultiSelectWithSelectAllComponent {
  @Input() options: any[] = [];
  @Input() control: FormControl = new FormControl([]);
  @Output() selectionChange = new EventEmitter<any[]>();

  isAllSelected(): boolean {
    return this.control.value.length === this.options.length;
  }

  selectAll(event: any): void {
    if (event.checked) {
      this.control.setValue(this.options.map(option => option.value));
    } else {
      this.control.setValue([]);
    }
    this.selectionChange.emit(this.control.value);
  }
}
