import { Directive, HostListener, AfterViewInit, ViewChild, ElementRef, Renderer2 } from '@angular/core';
import { MultiSelect } from 'primeng/multiselect'; // Import the component class if available

@Directive({
  selector: '[appTrimFilter]'
})
export class TrimFilterDirective implements AfterViewInit {
  @ViewChild(MultiSelect) multiSelect!: MultiSelect;

  constructor(private renderer: Renderer2, private el: ElementRef) { }

  ngAfterViewInit() {
    // Ensure the component is initialized before trying to interact with it
  }

  @HostListener('onFilter', ['$event'])
  onFilter(event: any) {
    if (event?.filter && typeof event.filter === 'string') {
      // Trim the filter value
      const trimmedFilter = event.filter.trim();
      console.log('Trimmed filter value:', trimmedFilter);

      // Ensure that the filter input exists
      const filterInput = this.el.nativeElement.querySelector('input');
      if (filterInput) {
        // Set the value of the filter input
        this.renderer.setProperty(filterInput, 'value', trimmedFilter);
        
        // Dispatch an input event to ensure that the component updates its internal state
        const inputEvent = new Event('input', { bubbles: true });
        filterInput.dispatchEvent(inputEvent);

        // If needed, also trigger a change event
        const changeEvent = new Event('change', { bubbles: true });
        filterInput.dispatchEvent(changeEvent);

        console.log(filterInput)
      }
    }
  }
}
