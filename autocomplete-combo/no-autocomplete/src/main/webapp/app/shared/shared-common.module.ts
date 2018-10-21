import { NgModule } from '@angular/core';

import { NoautocompleteSharedLibsModule, FindLanguageFromKeyPipe, NoaAlertComponent, NoaAlertErrorComponent } from './';

@NgModule({
    imports: [NoautocompleteSharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, NoaAlertComponent, NoaAlertErrorComponent],
    exports: [NoautocompleteSharedLibsModule, FindLanguageFromKeyPipe, NoaAlertComponent, NoaAlertErrorComponent]
})
export class NoautocompleteSharedCommonModule {}
