import { NgModule } from '@angular/core';

import { AutocompleteentitySharedLibsModule, FindLanguageFromKeyPipe, AueAlertComponent, AueAlertErrorComponent } from './';

@NgModule({
    imports: [AutocompleteentitySharedLibsModule],
    declarations: [FindLanguageFromKeyPipe, AueAlertComponent, AueAlertErrorComponent],
    exports: [AutocompleteentitySharedLibsModule, FindLanguageFromKeyPipe, AueAlertComponent, AueAlertErrorComponent]
})
export class AutocompleteentitySharedCommonModule {}
