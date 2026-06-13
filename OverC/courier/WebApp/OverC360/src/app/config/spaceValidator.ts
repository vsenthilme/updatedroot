import { AbstractControl, ValidatorFn } from '@angular/forms';

export function noLeadingTrailingSpacesValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    if (control.value && (control.value as string).trim() !== control.value) {
      return { 'leadingTrailingSpaces': true };
    }
    return null;
  };
}
