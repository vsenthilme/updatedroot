// src/app/pdf.service.ts
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';

@Injectable({
  providedIn: 'root'
})
export class PdfService {

  private readonly API_KEY = 'eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIxIiwianRpIjoiMmE5MzU2M2M5MjE4MDQyOGM1ZDBkMzRkYTViMGU0NTFkMDNhOGZlOTExOWJjOTU0ZDRkNTFlOWE3ZWI1MWIzOTZiNWVjZGY4Zjc4NWJhYjMiLCJpYXQiOjE3MjM1MzMwMDcuMTkzODE0LCJuYmYiOjE3MjM1MzMwMDcuMTkzODE1LCJleHAiOjQ4NzkyMDY2MDcuMTkwNTA5LCJzdWIiOiI2OTI2NzU1OCIsInNjb3BlcyI6WyJ1c2VyLnJlYWQiLCJ1c2VyLndyaXRlIiwidGFzay5yZWFkIiwidGFzay53cml0ZSIsIndlYmhvb2sucmVhZCIsIndlYmhvb2sud3JpdGUiLCJwcmVzZXQucmVhZCIsInByZXNldC53cml0ZSJdfQ.Sui5uokzgbBuBNkk3o8G4EoHge68cJe5_jdOmoFtYQWQ82SZOMw6noeUDLmD9F7vDXZMSodB04y9VLUsILaILQNcalYScGOYP8fYdFv-WkeNrGVQObHVI6daBzRaHDKutudcKfCeOytdJB8H16GIts3mkKb_f3MssJ9zBZ-QX4HqRWA8jem71Liro7gNFAgIwZrbm9IuERw3uZzpU6304kwzOsF_HD0vjbBtDdR6VSoTJtbPgRP1yzDut4qIEQee2bfZ1OoJjS2yxPysJsDbo1M6r1Fgb9paf-SQ1llfKU1cU3F1oKIWEHRufsCn7bhyt24s8t2RWQMy0fgKkRkizgmXz3tL6dMRviir0M0FB87-r5Aj-2EnlxaMLS2q0hk7S4AKDJLPDDUXUibCASsKocF3ufa3I1afAKlwUtAOOGlQD4rr5iwjcFk-tRj2e9cnn_gUKUz471xXmDULX6WHXWePmlLWo8Cvb_lZ3y9InDn0sYfQINCBTULdPNMTnEoxUi0cbOeETfhpsWLBPnkcYcM2Fj5TsPs-PeaoL_MUolg1dZgjUCerF0rmmcseg4oTInrfeEJLjfGv-xgljHnxNjpUyQXDg2SIFOs98K34YFycBlMJpu2QQg4o7RjVaFY20wGRVVOhj-LLvVPat4x6YGT3kZgTVBsVWSxo8NLeIBg'; 

  private readonly CLOUDCONVERT_API_URL = 'https://api.cloudconvert.com/v2';

  constructor(private http: HttpClient) {
  }

  async uploadAndCompressPdf(blob: Blob): Promise<void> {
    try {
      // Step 1: Upload the PDF
      const uploadResponse = await this.http.post<any>(
        `${this.CLOUDCONVERT_API_URL}/import/upload`,
        {},
        {
          headers: { 'Authorization': `Bearer ${this.API_KEY}` }
        }
      ).toPromise();

      const uploadId = uploadResponse.data.id;

      // Create FormData to upload file
      const formData = new FormData();
      formData.append('file', blob, 'document.pdf');

      await this.http.post(`https://storage.cloudconvert.com/upload/${uploadId}`, formData).toPromise();

      // Step 2: Compress the PDF
      const conversionResponse = await this.http.post<any>(
        `${this.CLOUDCONVERT_API_URL}/convert`,
        {
          input: uploadId,
          output_format: 'pdf',
          file: 'document.pdf',
          options: {
            'compress': true
          }
        },
        {
          headers: { 'Authorization': `Bearer ${this.API_KEY}` }
        }
      ).toPromise();

      const conversionId = conversionResponse.data.id;

      // Poll for conversion status
      let result: any;
      while (true) {
        const statusResponse = await this.http.get<any>(
          `${this.CLOUDCONVERT_API_URL}/convert/${conversionId}`,
          {
            headers: { 'Authorization': `Bearer ${this.API_KEY}` }
          }
        ).toPromise();
        
        result = statusResponse.data;
        if (result.data.status === 'finished') break;
        await new Promise(r => setTimeout(r, 3000)); // Poll every 3 seconds
      }

      // Step 3: Download the compressed PDF
      const downloadUrl = result.data.result.files[0].url;
      const downloadResponse = await this.http.get(downloadUrl, { responseType: 'blob' }).toPromise() as Blob;
      
      if (downloadResponse) {
        const a = document.createElement('a');
        a.href = window.URL.createObjectURL(downloadResponse);
        a.download = 'compressed.pdf';
        document.body.appendChild(a);
        a.click();
        document.body.removeChild(a);
        console.log('Compressed PDF downloaded.');
      } else {
        console.error('Download response is undefined.');
      }
      
    } catch (error) {
      console.error('Error compressing PDF:', error);
    }
  }
}
