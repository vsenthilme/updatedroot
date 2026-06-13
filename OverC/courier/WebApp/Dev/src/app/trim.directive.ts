import { Directive, ElementRef, HostListener } from '@angular/core';

@Directive({
  selector: '[appTrim]'
})
export class TrimDirective {
  constructor(private el: ElementRef) {}

  @HostListener('paste', ['$event'])
  onPaste(event: ClipboardEvent): void {
    const clipboardData = event.clipboardData || (window as any).clipboardData;
    const pastedText = clipboardData.getData('text');
    const trimmedText = pastedText.trim();

    event.preventDefault();

    document.execCommand('insertText', false, trimmedText);
  }

  @HostListener('blur', ['$event'])
  onBlur(): void {
    const inputElement = this.el.nativeElement as HTMLInputElement;
    inputElement.value = inputElement.value.trim();
  }
}
