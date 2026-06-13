import { Directive, ElementRef, HostListener, OnInit } from '@angular/core';

@Directive({
  selector: '[appTrimDropdownFilter]'
})
export class TrimDropdownFilterDirective implements OnInit {
  private filterInput: HTMLInputElement | null = null;

  constructor(private el: ElementRef) {}

  ngOnInit() {
    // Get the p-dropdown element
    this.el.nativeElement.addEventListener('click', () => {
      setTimeout(() => {
        // Find the filter input within the p-dropdown component
        this.filterInput = this.el.nativeElement.querySelector('.p-dropdown-filter');
      }, 0);
    });
  }

  @HostListener('input', ['$event'])
  onInput(event: Event) {
    if (this.filterInput) {
      this.filterInput.value = this.filterInput.value.trim();
    }
  }

  @HostListener('keydown', ['$event'])
  onKeydown(event: KeyboardEvent) {
    if (this.filterInput) {
      // Handle the filtering and trimming on keydown
      this.filterInput.value = this.filterInput.value.trim();
    }
  }
}
